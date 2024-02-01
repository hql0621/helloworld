package org.com.fisco;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.client.protocol.response.BlockNumber;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;

import java.math.BigInteger;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        String configFile = "src/main/resources/config-example.toml";
        // 初始化BcosSDK
        BcosSDK sdk =  BcosSDK.build(configFile);
        // 为群组1初始化client
        Client client = sdk.getClient(Integer.valueOf(1));
        // 构造AssembleTransactionProcessor对象，需要传入client对象，CryptoKeyPair对象和abi、binary文件存放的路径。abi和binary文件需要在上一步复制到定义的文件夹中。
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        // 获取群组1的块高
        //BlockNumber blockNumber = client.getBlockNumber();

        // 向群组1部署HelloWorld合约
        //CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
        //HelloWorld helloWorld = HelloWorld.deploy(client, cryptoKeyPair);
        //HelloWorld helloWorld = HelloWorld.load("0xe5846720fe45909ab5bee3f06e88c8732b033aee",client, keyPair);
        AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "src/main/resources/contract/abi/", "src/main/resources/contract/bin/");

        // 部署合约。第一个参数为合约名称，第二个参数为合约构造函数的列表，是List<Object>类型。
        //TransactionResponse responseTable = transactionProcessor.deployByContractLoader("Table", new ArrayList<>());
        //TransactionResponse responseKVTableTest = transactionProcessor.deployByContractLoader("KVTableTest", new ArrayList<>());
        //System.out.println("responseTable:"+responseTable);
        //System.out.println("responseKVTableTest:"+responseKVTableTest);

        // 调用HelloWorld合约的get接口
        //String getValue = helloWorld.get();

        //获取合约地址
        String TableAddress = "0x0a8d31f5878e0b0b7a3fdea021d0252eeeb59206";
        String KVTableTestAddress = "0x81b1bd3ae2ef453a4cf8eb025564883ab07247e4";
        //System.out.println("TableAddress:"+TableAddress);
        //System.out.println("KVTableTestAddress:"+KVTableTestAddress);
        KVTableTest kvTableTest = KVTableTest.load(KVTableTestAddress,client,keyPair);

        //发送交易
        TransactionReceipt receipt = kvTableTest.set("100010001001", BigInteger.valueOf(6000),"Laptop");

        // 调用HelloWorld合约的set接口
        //TransactionReceipt receipt = helloWorld.set("0x10002, getput%^&**");
        //receipt = helloWorld.set("232323, getput%^&**");
        System.out.println(receipt);
        // 调用HelloWorld合约的get接口
        String getValue = kvTableTest.get("100010001001").toString();
        System.out.println("getValue: "+getValue);
        System.exit(1);
    }
}