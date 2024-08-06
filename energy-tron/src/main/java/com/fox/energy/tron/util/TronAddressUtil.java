package com.fox.energy.tron.util;


import com.fox.energy.common.core.domain.tron.TronAddressCreateModel;
import org.tron.trident.key.KeyPair;

public class TronAddressUtil {

    public static TronAddressCreateModel create() {
        KeyPair keyPair = KeyPair.generate();
        return new TronAddressCreateModel(keyPair.toBase58CheckAddress(), keyPair.toHexAddress(), keyPair.toPrivateKey(), keyPair.toPublicKey());
    }


    /**
     * 根据私钥获取地址
     *
     * @param privateKey
     * @return
     */
    public static String getAddressByPrivateKey(String privateKey) {
        KeyPair keyPair = new KeyPair(privateKey);
        return keyPair.toBase58CheckAddress();
    }
}
