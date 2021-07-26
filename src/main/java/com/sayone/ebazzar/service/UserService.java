package com.sayone.ebazzar.service;

import com.sayone.ebazzar.dto.AddressDto;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.entity.AddressEntity;
import com.sayone.ebazzar.entity.PasswordResetTokenEntity;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.exception.RequestException;
import com.sayone.ebazzar.model.request.AddressRequestModel;
import com.sayone.ebazzar.model.request.PasswordResetRequestModel;
import com.sayone.ebazzar.model.request.UserDetailsRequestModel;
import com.sayone.ebazzar.model.request.UserUpdateRequestModel;
import com.sayone.ebazzar.model.response.AddressResponseModel;
import com.sayone.ebazzar.model.response.UserRestModel;
import com.sayone.ebazzar.model.response.UserUpdateResponseModel;
import com.sayone.ebazzar.repository.AddressRepository;
import com.sayone.ebazzar.repository.PasswordResetTokenRepository;
import com.sayone.ebazzar.repository.UserRepository;
import com.sayone.ebazzar.utilities.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    EmailService emailService;

    public UserRestModel createUser(UserDetailsRequestModel userDetailsRequestModel){
        UserRestModel returnValue = new UserRestModel();
        UserDto user= new UserDto();
        BeanUtils.copyProperties(userDetailsRequestModel,user);
        List<AddressDto> addressDtos = new ArrayList<AddressDto>();
        for (int i = 0; i < userDetailsRequestModel.getAddress().size(); i++) {
            AddressDto addressDto = new AddressDto();
            BeanUtils.copyProperties(userDetailsRequestModel.getAddress().get(i), addressDto);       //
            addressDto.setUser(user);
            addressDtos.add(addressDto);
        }
        user.setAddressDtos(addressDtos);
        if(userRepository.findByEmail(user.getEmail()) != null) throw new RequestException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessages());
        for (int i =0; i<user.getAddressDtos().size();i++){
            AddressDto addressDto=user.getAddressDtos().get(i);
            addressDto.setUser(user);
            user.getAddressDtos().set(i,addressDto);
        }
        UserEntity userEntity=new UserEntity();
        BeanUtils.copyProperties(user,userEntity);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        List<AddressEntity> addressEntities = new ArrayList<AddressEntity>();
        for (int i=0;i<user.getAddressDtos().size();i++){
            AddressDto addressDto=user.getAddressDtos().get(i);
            AddressEntity addressEntity=new AddressEntity();
            BeanUtils.copyProperties(addressDto,addressEntity);
            addressEntities.add(addressEntity);
        }
        userEntity.setAddress(addressEntities);
        userEntity.setUserStatus(true);
        UserEntity storedUserDetails=userRepository.save(userEntity);
        List<AddressResponseModel> addressResponseModels = new ArrayList<AddressResponseModel>();
        for (int i =0;i<storedUserDetails.getAddress().size();i++){
            AddressResponseModel addressResponseModel=new AddressResponseModel();
            AddressEntity addressEntity = storedUserDetails.getAddress().get(i);
            BeanUtils.copyProperties(addressEntity,addressResponseModel);
            addressResponseModels.add(addressResponseModel);
        }
        returnValue.setAddressResponseModels(addressResponseModels);
        BeanUtils.copyProperties(storedUserDetails,returnValue);

        return returnValue;
    }

    public UserUpdateResponseModel updateUser(UserUpdateRequestModel updateRequestModel, String email){
        UserUpdateResponseModel returnValue = new UserUpdateResponseModel();
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(updateRequestModel,userEntity);
        UserEntity userToUpdate=userRepository.findByEmail(email);
        if (userToUpdate==null) throw new UsernameNotFoundException(email);
        userToUpdate.setFirstName(updateRequestModel.getFirstName());
        userToUpdate.setLastName(updateRequestModel.getLastName());
        userToUpdate.setPhoneNumber(updateRequestModel.getPhoneNumber());
        userRepository.save(userToUpdate);
        BeanUtils.copyProperties(userToUpdate,returnValue);
        return returnValue;
    }

    public UserRestModel getUserByEmail(String email) {
        UserRestModel returnValue = new UserRestModel();
        UserEntity userEntity=userRepository.findByEmail(email);
        if (userEntity==null) throw new UsernameNotFoundException(email);
        BeanUtils.copyProperties(userEntity, returnValue);
        List<AddressDto> addressDtos = new ArrayList<AddressDto>();
        for(AddressEntity addressEntity:userEntity.getAddress())
        {
            AddressDto addressDto= new AddressDto();
            BeanUtils.copyProperties(addressEntity,addressDto);
            addressDtos.add(addressDto);
        }
        UserDto userAddress = new UserDto();
        userAddress.setAddressDtos(addressDtos);
        List<AddressResponseModel> addressResponseModels = new ArrayList<AddressResponseModel>();
        for (AddressDto addressDto : userAddress.getAddressDtos())
        {
            AddressResponseModel addressResponseModel = new AddressResponseModel();
            BeanUtils.copyProperties(addressDto, addressResponseModel);
            addressResponseModels.add(addressResponseModel);
        }
        returnValue.setAddressResponseModels(addressResponseModels);
        return returnValue;
    }

    public UserDto getUser(String email)
    {
        UserEntity userEntity=userRepository.findByEmail(email);
        if (userEntity==null) throw new UsernameNotFoundException(email);
        UserDto returnValue=new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    public boolean requestPasswordReset(UserDto userDto,String url) throws MessagingException,UnsupportedEncodingException{

        boolean returnValue = false;
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);
        String token=new Utils().generatePasswordResetToken(userEntity.getEmail());
        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserDetails(userEntity);
        passwordResetTokenRepository.save(passwordResetTokenEntity);
        triggerMailForPasswordReset(passwordResetTokenEntity,userEntity,url);
        returnValue=true;
        return  returnValue;
    }

    public void triggerMailForPasswordReset(
            PasswordResetTokenEntity passwordResetTokenEntity,
            UserEntity userEntity,
            String url)
            throws MessagingException, UnsupportedEncodingException {

        emailService.sendSimpleEmailForPasswordReset(passwordResetTokenEntity,userEntity,url);
    }

    public boolean verifyPasswordResetToken(String email, String token, PasswordResetRequestModel passwordResetRequestModel){
        boolean returnValue = false;
        UserEntity userEntity=userRepository.findByEmail(email);
        String encryptedNewPassword=bCryptPasswordEncoder.encode(passwordResetRequestModel.getNewPassword());
        boolean hastokenExpired = Utils.hasTokenExpired(token);
        if (!hastokenExpired) {
            System.out.println("Not Expired");
            userEntity.setEncryptedPassword(encryptedNewPassword);
            userEntity.setPassword(passwordResetRequestModel.getNewPassword());
            userRepository.save(userEntity);
            returnValue = true;
        }
      return  returnValue;
    }

    public AddressResponseModel addAddress(long userId, AddressRequestModel addressRequestModel){
        AddressResponseModel returnValue=new AddressResponseModel();
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(userId);
        AddressDto addressDto=new AddressDto();
        BeanUtils.copyProperties(addressRequestModel,addressDto);
        AddressEntity addressEntity=new AddressEntity();
        BeanUtils.copyProperties(addressDto,addressEntity);
        addressEntity.setUser(userEntity);
        AddressEntity storedAddress=addressRepository.save(addressEntity);
        BeanUtils.copyProperties(storedAddress,addressDto);
        BeanUtils.copyProperties(addressDto,returnValue);
        return returnValue;
    }

    public void deleteUser(String email)
    {
        UserEntity userEntity=userRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);
        userEntity.setUserStatus(false);
        userRepository.save(userEntity);

    }

    @Override
    public UserDetails loadUserByUsername(String email)  {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);
        userEntity.setUserStatus(getUser(email).getUserStatus());
        if(userEntity.getUserStatus()==false) throw new RequestException(ErrorMessages.DELETED_ACCOUNT.getErrorMessages());
        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),new ArrayList<>());
    }
}
