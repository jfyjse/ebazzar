package com.sayone.ebazzar.service;

import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.entity.CartItemEntity;
import com.sayone.ebazzar.entity.Product;
import com.sayone.ebazzar.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartService {
    @Autowired
    CartItemRepository cartItemRepository;
    CartEntity cartEntity = new CartEntity();
    Product productEntity = new Product();
    public List<CartEntity> getCartListById() {
        return null;
    }
    public void addCartItem(CartItemEntity itemEntity){
        Long productId= itemEntity.getProduct().getProductId();
        List<CartItemEntity> cartItemEntities = cartItemRepository.findAll();

        for(int i=0;i<cartItemEntities.size();i++){
            if(cartItemEntities.get(i).getProduct().getProductId().equals(productId)){
                cartItemEntities.get(i).setQuantity(cartItemEntities.get(i).getQuantity()+1);
                cartItemEntities.get(i).setTotalPrice(cartItemEntities.get(i).getQuantity() * itemEntity.getProduct().getPrice());
                System.out.println(" cart items id = "+ cartItemEntities.size());
                updateGrandTotal();
                return;
            }
        }
        cartItemEntities.add(itemEntity);
        updateGrandTotal();
    }

    private void updateGrandTotal() {
        cartEntity.setGrantTotal(0);
        Double grantTotal= cartEntity.getGrantTotal();
        for(CartItemEntity itemEntity : cartEntity.getCartItemEntities()){
            grantTotal=grantTotal+ itemEntity.getTotalPrice();

        }
        cartEntity.setGrantTotal(grantTotal);
    }

    private void removeCartItem(CartItemEntity item){
        List<CartItemEntity> cartItemEntities = cartItemRepository.findAll();
        Long productId = item.getProduct().getProductId();
        for(int j = 0 ; j < cartItemEntities.size() ;j++ ){

            if(cartItemEntities.get(j).getProduct().getProductId().equals(productId)){
                if (cartItemEntities.get(j).getQuantity()!=1){
                    cartItemEntities.get(j).setQuantity(cartItemEntities.get(j).getQuantity()-1);
                    cartItemEntities.get(j).setTotalPrice(cartItemEntities.get(j).getQuantity() * item.getProduct().getPrice());
                }
                else {
                    cartItemEntities.remove(cartItemEntities.get(j));
                }
                updateGrandTotal();
                return;
            }
        }
    }

}
