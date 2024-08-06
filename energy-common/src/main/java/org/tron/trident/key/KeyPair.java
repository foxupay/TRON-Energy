package org.tron.trident.key;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;
import org.tron.trident.crypto.SECP256K1;
import org.tron.trident.crypto.tuwenitypes.Bytes32;
import org.tron.trident.utils.Base58Check;

public class KeyPair {
    private SECP256K1.KeyPair rawPair;

    public KeyPair(SECP256K1.KeyPair keyPair) {
        this.rawPair = keyPair;
    }

    public KeyPair(String hexPrivateKey) {
        this.rawPair = SECP256K1.KeyPair.create(SECP256K1.PrivateKey.create(Bytes32.fromHexString(hexPrivateKey)));
    }

    public SECP256K1.KeyPair getRawPair() {
        return this.rawPair;
    }

    public static KeyPair generate() {
        return new KeyPair(org.tron.trident.crypto.SECP256K1.KeyPair.generate());
    }

    public String toPrivateKey() {
        return Hex.toHexString(this.rawPair.getPrivateKey().getEncoded());
    }

    public String toPublicKey() {
        return Hex.toHexString(this.rawPair.getPublicKey().getEncoded());
    }

    public String toBase58CheckAddress() {
        SECP256K1.PublicKey pubKey = this.rawPair.getPublicKey();
        return publicKeyToBase58CheckAddress(pubKey);
    }

    public String toHexAddress() {
        SECP256K1.PublicKey pubKey = this.rawPair.getPublicKey();
        return publicKeyToHexAddress(pubKey);
    }

    public static byte[] publicKeyToAddress(SECP256K1.PublicKey pubKey) {
        Keccak.Digest256 digest = new Keccak.Digest256();
        digest.update(pubKey.getEncoded(), 0, 64);
        byte[] raw = digest.digest();
        byte[] rawAddr = new byte[21];
        rawAddr[0] = 65;
        System.arraycopy(raw, 12, rawAddr, 1, 20);
        return rawAddr;
    }

    public static String publicKeyToBase58CheckAddress(SECP256K1.PublicKey pubKey) {
        byte[] rawAddr = publicKeyToAddress(pubKey);
        return Base58Check.bytesToBase58(rawAddr);
    }

    public static String publicKeyToHexAddress(SECP256K1.PublicKey pubKey) {
        byte[] rawAddr = publicKeyToAddress(pubKey);
        return Hex.toHexString(rawAddr);
    }

    public static byte[] signTransaction(byte[] txid, KeyPair keyPair) {
        SECP256K1.KeyPair kp = keyPair.getRawPair();
        SECP256K1.Signature sig = SECP256K1.sign(Bytes32.wrap(txid), kp);
        return sig.encodedBytes().toArray();
    }
}
