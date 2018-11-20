package com.mofei.tau.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mofei.tau.R;
import com.mofei.tau.constant.TAU_BaseURL;
import com.mofei.tau.db.greendao.TransactionHistoryDaoUtils;
import com.mofei.tau.db.greendao.UTXORecordDaoUtils;
import com.mofei.tau.entity.req_parameter.FBAddressEmail;
import com.mofei.tau.entity.req_parameter.FBAddressEmlVeri;
import com.mofei.tau.entity.req_parameter.FBAddressPubKey;
import com.mofei.tau.entity.res_post.Balance;
import com.mofei.tau.entity.res_post.BalanceRet;
import com.mofei.tau.entity.req_parameter.FBAddress;
import com.mofei.tau.entity.res_post.Club;
import com.mofei.tau.entity.res_post.ClubRet;
import com.mofei.tau.entity.res_post.Height;
import com.mofei.tau.entity.res_post.Login1;
import com.mofei.tau.entity.res_post.Login1Ret;
import com.mofei.tau.entity.res_post.Login1RetSerializer;
import com.mofei.tau.entity.res_post.RawTX;
import com.mofei.tau.entity.res_post.RawTXRet;
import com.mofei.tau.entity.res_post.RawTXRetVin;
import com.mofei.tau.entity.res_post.RawTXRetVinScriptSig;
import com.mofei.tau.entity.res_post.RawTXRetVout;
import com.mofei.tau.entity.res_post.RawTXRetVoutScriptPubKey;
import com.mofei.tau.entity.res_post.ReferralURL;
import com.mofei.tau.entity.res_post.StatusMessage;
import com.mofei.tau.entity.res_post.TalkUpdateRet;
import com.mofei.tau.entity.res_post.UTXOBean;
import com.mofei.tau.entity.res_post.UTXOList;
import com.mofei.tau.entity.res_post.UTXOListRet;
import com.mofei.tau.entity.res_post.UTXOListRetScriptPubkey;
import com.mofei.tau.entity.res_put.BuyCoins;
import com.mofei.tau.entity.res_put.BuyCoinsRet;
import com.mofei.tau.entity.res_put.Login0;
import com.mofei.tau.entity.res_put.Login0Ret;
import com.mofei.tau.info.SharedPreferencesHelper;
import com.mofei.tau.net.ApiService;
import com.mofei.tau.net.NetWorkManager;
import com.mofei.tau.transaction.ScriptPubkey;
import com.mofei.tau.transaction.TransactionHistory;
import com.mofei.tau.transaction.UTXORecord;
import com.mofei.tau.util.L;
import com.mofei.tau.util.MD5_BASE64Util;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TextActivity extends BaseActivity implements View.OnClickListener{

    private static String TAG = "FacebookLoginDemo";
    String userId;
    String Address;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        String fbid= SharedPreferencesHelper.getInstance(TextActivity.this).getString("userId","userId");

        address=SharedPreferencesHelper.getInstance(TextActivity.this).getString("Address","Address");

        Log.i(TAG,fbid +" "+address);

        userId=SharedPreferencesHelper.getInstance(TextActivity.this).getString("userId","userId");

        Address=SharedPreferencesHelper.getInstance(TextActivity.this).getString("Address","Address");

        findViewById(R.id.Balance).setOnClickListener(this);
        findViewById(R.id.login0).setOnClickListener(this);
        findViewById(R.id.club).setOnClickListener(this);
        findViewById(R.id.submitEmail).setOnClickListener(this);
        findViewById(R.id.confirmEmail).setOnClickListener(this);
        findViewById(R.id.TalkUpdate).setOnClickListener(this);
        findViewById(R.id.talkUpload).setOnClickListener(this);
        findViewById(R.id.buyCoins).setOnClickListener(this);
        findViewById(R.id.login1).setOnClickListener(this);
        findViewById(R.id.referralURL).setOnClickListener(this);

        findViewById(R.id.insert).setOnClickListener(this);

        findViewById(R.id.getHeight).setOnClickListener(this);
        findViewById(R.id.getUTXOList).setOnClickListener(this);
        findViewById(R.id.getRawTX).setOnClickListener(this);

        findViewById(R.id.delete).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Balance:
             //   getData();
                break;
            case R.id.login0:
                getData0();
                break;
            case R.id.club:
                getData1();
                break;
            case R.id.submitEmail:
                getData2();
                break;
            case R.id.confirmEmail:
                getData3();
                break;
            case R.id.TalkUpdate:
                getData4();
                break;

            case R.id.talkUpload:
                //getData5();
                break;

            case R.id.buyCoins:
                getData6();
                break;
            case R.id.login1:
                getData7();
                break;
            case R.id.referralURL:
                getData8();
                break;

            case R.id.insert:

               /* L.e("111");
                User user = new User();
                user.setAge(10);
                user.setName("ly");
                MyApplication.getInstances().getDaoSession().insert(user);
                Log.i("-------", "添加成功");*/
                /*L.e("111");
                TransactionHistory transactionHistory=new TransactionHistory();
                transactionHistory.setTxId("setTxId");
                transactionHistory.setResult(true);
                transactionHistory.setToAddress("dsdsafafdaaas");
                transactionHistory.setValue("1111");
                transactionHistory.setTime(1000000);
                TransactionHistoryDaoUtils.getInstance().insertTransactionHistoryData(transactionHistory);
*/
               // TransactionHistoryDaoUtils.getInstance().deleteTransactionHistoryData(transactionHistory);

               // TransactionHistoryDaoUtils.getInstance().deleteTransactionHistoryByKey(transactionHistory.getId());
               // TransactionHistoryDaoUtils.getInstance().deleteAllData();
                /* UTXORecord utxoRecord=new UTXORecord();
                utxoRecord.setAddress("xinye");
                utxoRecord.setBestblockhash("haxihaxihaxi");
                utxoRecord.setCoinbase(true);
                utxoRecord.setTxId("111");
                UTXORecordDaoUtils.getInstance().insertUTXORecordData(utxoRecord);*/

                TransactionHistoryDaoUtils.getInstance().deleteAllData();
                L.e("shanchu成功");

               /* List<TransactionHistory> unComformTX=TransactionHistoryDaoUtils.getInstance().queryTransactionHistoryByParams("where CONFIRMATIONS<?", String.valueOf(2));
                for (int i=0;i<unComformTX.size();i++){
                    L.e("unComformTX"+i);
                }
                L.e("成功");*/
                break;

            case R.id.getHeight:
                getHeight();
                break;

            case R.id.getUTXOList:
                L.e("getUTXOList");
                getUTXOList();
                break;
            case R.id.getRawTX:
                getRawTX();
                break;
            case R.id.sendRawTransation:
                sendRawTransation();
                break;
            case R.id.delete:
               // TransactionHistoryDaoUtils.getInstance().deleteAllData();
                UTXORecordDaoUtils.getInstance().deleteAllData();
                break;

        }
    }

    private void sendRawTransation() {

    }

    private void getRawTX() {

        Map<String,String> txid=new HashMap<>();

        txid.put("txid","28b15b032a6e0bc3f2a43ba8d7ec5d2b8377c5898f1dfbbe6b4bac0e90657bd7");
        ApiService apiService=NetWorkManager.getApiService();
        Observable<RawTX> observable=apiService.getRawTransation(txid);
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RawTX>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RawTX rawTX) {

                        TransactionHistory transactionHistory=new TransactionHistory();
                        L.e(list.get(0).getTxid());
                        transactionHistory.setTxId(list.get(0).getTxid());
                        transactionHistory.setConfirmations(rawTX.getRet().getConfirmations());
                        transactionHistory.setResult(true);
                        transactionHistory.setToAddress("sdsddfageaggeag");
                        L.e(list.get(0).getValue());
                        transactionHistory.setValue(list.get(0).getValue());
                        L.e(""+list.get(0).getBlocktime());
                        transactionHistory.setTime("");

                        TransactionHistoryDaoUtils.getInstance().insertTransactionHistoryData(transactionHistory);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    List<UTXORecord> utxoRecordList;
    List<UTXOList.UtxosBean> list;

    private void getUTXOList() {

        Map<String,String> address=new HashMap<>();

        String addre=SharedPreferencesHelper.getInstance(TextActivity.this).getString("Address","Address");
        L.e("Address: "+addre);
        String pub=SharedPreferencesHelper.getInstance(TextActivity.this).getString("Pubkey","Pubkey");
        L.e("publ: "+pub);
        String Priv=SharedPreferencesHelper.getInstance(TextActivity.this).getString("Privkey","Privkey");
        L.e("Privkey: "+Priv);

        address.put("address",addre);
        ApiService apiService=NetWorkManager.getApiService();
        Observable<UTXOList> observable=apiService.getUTXOList(address);
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UTXOList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UTXOList utxoListRetUTXOList) {

                        L.e("Message : "+utxoListRetUTXOList.getMessage());
                        L.e("Status : "+utxoListRetUTXOList.getStatus());
                        L.e("utxos-size :"+utxoListRetUTXOList.getUtxosX().size());

                        utxoRecordList=new ArrayList<>();
                        list=utxoListRetUTXOList.getUtxosX();
                        for (int i=0;i<list.size();i++){

                            L.e("getTxid :"+utxoListRetUTXOList.getUtxosX().get(i).getTxid());
                            L.e("getAddresses :"+utxoListRetUTXOList.getUtxosX().get(i).getScriptPubKey().getAddresses().get(0));
                            L.e("getVout :"+utxoListRetUTXOList.getUtxosX().get(i).getVout());
                            L.e("getValue :"+utxoListRetUTXOList.getUtxosX().get(i).getValue());
                            L.e("getConfirmations :"+utxoListRetUTXOList.getUtxosX().get(i).getConfirmations());
                            L.e("getAsm :"+utxoListRetUTXOList.getUtxosX().get(i).getScriptPubKey().getAsm());
                            L.e("getHex :"+utxoListRetUTXOList.getUtxosX().get(i).getScriptPubKey().getHex());

                            L.e("getBlockhash :"+utxoListRetUTXOList.getUtxosX().get(i).getBlockhash());
                            L.e("getBlockheight :"+utxoListRetUTXOList.getUtxosX().get(i).getBlockheight());
                            L.e("getBlocktime :"+utxoListRetUTXOList.getUtxosX().get(i).getBlocktime());

                            L.e("getBestblockhash :"+utxoListRetUTXOList.getUtxosX().get(i).getBestblockhash());
                            L.e("getBestblocktime :"+utxoListRetUTXOList.getUtxosX().get(i).getBestblocktime());
                            L.e("getBestblockheight :"+utxoListRetUTXOList.getUtxosX().get(i).getBestblockheight());

                            L.e("getVersion :"+utxoListRetUTXOList.getUtxosX().get(i).getVersion());

                            L.e("isCoinbase :"+utxoListRetUTXOList.getUtxosX().get(i).isCoinbase());

                            L.e("getAddresses :"+utxoListRetUTXOList.getUtxosX().get(i).getScriptPubKey().getAddresses());

                            L.e("========================"+i);

                            UTXORecord utxoRecord=new UTXORecord();
                            utxoRecord.setConfirmations(utxoListRetUTXOList.getUtxosX().get(i).getConfirmations());
                            utxoRecord.setTxId(utxoListRetUTXOList.getUtxosX().get(i).getTxid());

                            utxoRecord.setVout(utxoListRetUTXOList.getUtxosX().get(i).getVout());
                            //utxoRecord.setVout(1L);
                            utxoRecord.setAddress(utxoListRetUTXOList.getUtxosX().get(i).getScriptPubKey().getAddresses().get(0));


                            ScriptPubkey scriptPubkey =new ScriptPubkey();
                            scriptPubkey.setAsm(utxoListRetUTXOList.getUtxosX().get(i).getScriptPubKey().getAsm());
                            scriptPubkey.setHex(utxoListRetUTXOList.getUtxosX().get(i).getScriptPubKey().getHex());
                            //scriptPubkey.setReqSigs(utxoListRetUTXOList.getUtxosX().get(i).getScriptPubKey().getReqSigs());
                           // scriptPubkey.setType(utxoListRetUTXOList.getUtxosX().get(i).getScriptPubKey().getType());
                           // scriptPubkey.setAddress(utxoListRetUTXOList.getUtxosX().get(i).getScriptPubKey().getAddresses().get(0));
                            utxoRecord.setScriptPubKey(scriptPubkey);
                            utxoRecord.setVersion(utxoListRetUTXOList.getUtxosX().get(i).getVersion());
                            utxoRecord.setCoinbase(utxoListRetUTXOList.getUtxosX().get(i).isCoinbase());
                            utxoRecord.setBestblockhash(utxoListRetUTXOList.getUtxosX().get(i).getBestblockhash());
                            utxoRecord.setBestblockheight(utxoListRetUTXOList.getUtxosX().get(i).getBlockheight());
                            utxoRecord.setBestblocktime(utxoListRetUTXOList.getUtxosX().get(i).getBestblocktime());
                            utxoRecord.setBlockhash(utxoListRetUTXOList.getUtxosX().get(i).getBlockhash());
                            utxoRecord.setBlockheight(utxoListRetUTXOList.getUtxosX().get(i).getBlockheight());
                            utxoRecord.setBlocktime(utxoListRetUTXOList.getUtxosX().get(i).getBlocktime());

                            L.e("getValue="+utxoListRetUTXOList.getUtxosX().get(i).getValue());
                            L.e("getValue="+Double.parseDouble(utxoListRetUTXOList.getUtxosX().get(i).getValue())*Math.pow(10,8));
                            L.e(""+utxoListRetUTXOList.getUtxosX().get(i).getValue());
                            //把5.00000000转化成50000000
                            Double d = new Double(utxoListRetUTXOList.getUtxosX().get(i).getValue())*Math.pow(10,8);
                            L.e("double"+d);
                            java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
                            nf.setGroupingUsed(false);
                            L.e("d:="+nf.format(d));

                            utxoRecord.setValue( new BigInteger(nf.format(d)));

                            utxoRecordList.add(utxoRecord);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        L.e("onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        L.e("onComplete");

                        UTXORecordDaoUtils.getInstance().insertOrReplaceMultiData(utxoRecordList);

                    }
                });
    }

    private void getHeight() {

        ApiService apiService=NetWorkManager.getApiService();
        Observable<Height> observable=apiService.getHeight();
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Height>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Height height) {

                        L.e(TAG,"Message: "+height.getMessage());
                        L.e(TAG,"Status: "+height.getStatus());
                        L.e(TAG,"height: "+height.getHeight());
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("onError");
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {

                        L.e("onComplete");
                    }
                });
    }



    private void getData8() {

        FBAddress fbAddress=new FBAddress();
        fbAddress.setFacebookid(userId );
        fbAddress.setAddress(Address );
        Log.i(TAG,"userId"+userId+"  Address  "+Address);

        ApiService apiService=NetWorkManager.getApiService();
        Observable<ReferralURL> observable=apiService.getreferralURL(fbAddress);
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReferralURL>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReferralURL referralURL) {
                        Log.i(TAG,"Message: "+referralURL.getMessage());
                        Log.i(TAG,"Status: "+referralURL.getStatus());
                        Log.i(TAG,"Referral_urlp: "+referralURL.getReferral_url());

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i(TAG,"onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,""+"onComplete");

                    }
                });
    }

    private void getData7() {

        FBAddress fbAddress=new FBAddress();
        fbAddress.setFacebookid("100028423540051" );
       // fbAddress.setAddress(Address );
        Log.i(TAG,"userId"+userId+"  Address  "+Address);

        ApiService apiService=NetWorkManager.getApiService();
        Observable<Login1<Login1Ret<Login1RetSerializer>>> observable=apiService.getLogin1(fbAddress);
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Observer<Login1<Login1Ret<Login1RetSerializer>>>() {
                     @Override
                     public void onSubscribe(Disposable d) {

                     }

                     @Override
                     public void onNext(Login1<Login1Ret<Login1RetSerializer>> login1RetLogin1) {

                         String Message=login1RetLogin1.getMessage();
                         Log.e(TAG, "Message: " +Message);
                         int Status=login1RetLogin1.getStatus();
                         Log.e(TAG, "Status: " +Status);
                         String address= login1RetLogin1.getRet().getSerializer_account().getAddress();
                         Log.e(TAG, "address: " +address);
                         String Pubkey= login1RetLogin1.getRet().getSerializer_account().getPubkey();
                         Log.e(TAG, "Pubkey : " +Pubkey);
                     }

                     @Override
                     public void onError(Throwable e) {
                         Log.i(TAG,"onError");
                      e.printStackTrace();
                     }

                     @Override
                     public void onComplete() {
                         Log.i(TAG,""+"onComplete");
                     }
                 });


    }

    private void getData6() {

        FBAddress fbAddress=new FBAddress();
        fbAddress.setFacebookid(userId );
        fbAddress.setAddress(Address );
        Log.i(TAG,"userId"+userId+"  Address  "+Address);

        ApiService apiService=NetWorkManager.getApiService();
         Observable<BuyCoins<BuyCoinsRet>> observable=apiService.getBuyCoins(fbAddress);
         observable.subscribeOn(Schedulers.io())
                 .subscribeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Observer<BuyCoins<BuyCoinsRet>>() {
                     @Override
                     public void onSubscribe(Disposable d) {

                     }

                     @Override
                     public void onNext(BuyCoins<BuyCoinsRet> buyCoins) {

                         Log.i(TAG,"Message: "+buyCoins.getMessage().toString());
                     }

                     @Override
                     public void onError(Throwable e) {

                         e.printStackTrace();
                         Log.i(TAG,""+e.toString());
                     }

                     @Override
                     public void onComplete() {

                         Log.i(TAG,""+"onComplete");
                     }
                 });
    }

    private void getData4() {
        FBAddress fbAddress=new FBAddress();
        fbAddress.setFacebookid(userId );
        fbAddress.setAddress(Address );

        Log.i(TAG,"userId"+userId+"  Address  "+Address);

        ApiService apiService=NetWorkManager.getApiService();
        Observable<Balance<TalkUpdateRet>> observable=apiService.getTalkUpdate(fbAddress);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Balance<TalkUpdateRet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Balance<TalkUpdateRet> balanceRet) {
                        Log.i(TAG,"Message: "+balanceRet.getMessage().toString());
                        Log.i(TAG,"Successful: "+balanceRet.getRet().getSuccessful());
                        Log.i(TAG,"Failed: "+balanceRet.getRet().getFailed());
                        Log.i(TAG,"Reviewing: "+balanceRet.getRet().getReviewing());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i(TAG,""+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,""+"onComplete");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //observable.unsubscribeOn(Schedulers.io());
    }

    private void getData3() {
        FBAddressEmlVeri fbAddressEmlVeri=new FBAddressEmlVeri();
        fbAddressEmlVeri.setFbid(userId);
        fbAddressEmlVeri.setAddress(Address);
        fbAddressEmlVeri.setVerification("127351");

        ApiService apiService=NetWorkManager.getApiService();
        Observable<StatusMessage> observable=apiService.getConfirmEmail(fbAddressEmlVeri);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatusMessage>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StatusMessage statusMessage) {
                        Log.i(TAG,"Message: "+statusMessage.getMessage().toString()+" status  "+statusMessage.getStatus());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,""+"onComplete");
                    }
                });
    }


    public void getData() {
        FBAddress fbAddress=new FBAddress();
        fbAddress.setFacebookid(SharedPreferencesHelper.getInstance(TextActivity.this).getString("userId","userId"));
        fbAddress.setAddress(SharedPreferencesHelper.getInstance(TextActivity.this).getString("Address","Address"));

        Log.i(TAG,"userId"+SharedPreferencesHelper.getInstance(TextActivity.this).getString("userId  ","userId")+"  Address  "+SharedPreferencesHelper.getInstance(TextActivity.this).getString("Address","Address"));

        ApiService apiService=NetWorkManager.getApiService();
        Observable<Balance<BalanceRet>> observable=apiService.getBalance(fbAddress);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Balance<BalanceRet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Balance<BalanceRet> balanceRet) {
                        Log.i(TAG,"Message: "+balanceRet.getMessage().toString());
                        Log.i(TAG,"Message: "+balanceRet.getRet().getCoins());
                        Log.i(TAG,"Message: "+balanceRet.getRet().getUtxo());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,""+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,""+"onComplete");
                    }
                });

    }

    public void getData0() {

        FBAddressPubKey fbAddressPubKey=new FBAddressPubKey();
        fbAddressPubKey.setFacebookid(SharedPreferencesHelper.getInstance(TextActivity.this).getString("userId","userId"));
        fbAddressPubKey.setAddress(SharedPreferencesHelper.getInstance(TextActivity.this).getString("Address","Address"));
        fbAddressPubKey.setPubkey(SharedPreferencesHelper.getInstance(TextActivity.this).getString("Pubkey","Pubkey"));

        ApiService apiService=NetWorkManager.getApiService();
        Observable<Login0<Login0Ret>> observable=apiService.getLogin0(fbAddressPubKey);

        observable.subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .subscribe(new Observer<Login0<Login0Ret>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Login0<Login0Ret> login0RetLogin0) {
                        Log.i(TAG,"Message: "+login0RetLogin0.getMessage().toString()+"   Status:"+login0RetLogin0.getStatus());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,""+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,""+"onComplete");
                    }
                });
    }




    /**
     *通过okhttpClient来设置证书
     * @param clientBuilder OKhttpClient.builder
     * @param certificates 读取证书的InputStream
     * */
    public void setCertificates(OkHttpClient.Builder clientBuilder, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory .generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {

                }
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance( TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            clientBuilder.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置https证书
    protected static SSLSocketFactory getSSLSocketFactory(Context context, int[] certificates) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }

        //CertificateFactory用来证书生成
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            //Create a KeyStore containing our trusted CAs
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                //读取本地证书
                InputStream is = context.getResources().openRawResource(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory
                        .generateCertificate(is));

                if (is != null) {
                    is.close();
                }
            }

            //Create a TrustManager that trusts the CAs in our keyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            //Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception e) {

        }
        return null;
    }

    public void getData1() {
        FBAddress fbAddress=new FBAddress();
        fbAddress.setFacebookid(SharedPreferencesHelper.getInstance(TextActivity.this).getString("userId","userId"));
        fbAddress.setAddress(SharedPreferencesHelper.getInstance(TextActivity.this).getString("Address","Address"));

        Log.i(TAG,"userId"+SharedPreferencesHelper.getInstance(TextActivity.this).getString("userId  ","userId")+"  Address  "+SharedPreferencesHelper.getInstance(TextActivity.this).getString("Address","Address"));

        ApiService apiService=NetWorkManager.getApiService();
        Observable<Club<ClubRet>> observable=apiService.getClub(fbAddress);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Club<ClubRet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Club<ClubRet> clubRetClub) {
                        Log.i(TAG,"Message: "+clubRetClub.getMessage().toString()+" Clubname  "+clubRetClub.getRet().getClubname());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,""+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,""+"onComplete");
                    }
                });

    }

    public void getData2() {
        FBAddressEmail fbAddressEmail=new FBAddressEmail();
        fbAddressEmail.setFbid(SharedPreferencesHelper.getInstance(TextActivity.this).getString("userId","userId"));
        fbAddressEmail.setAddress(SharedPreferencesHelper.getInstance(TextActivity.this).getString("Address","Address"));
        fbAddressEmail.setEmail("1095815930@qq.com");

        ApiService apiService=NetWorkManager.getApiService();
        Observable<StatusMessage> observable= apiService.getEmailVerification(fbAddressEmail);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatusMessage>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StatusMessage statusMessage) {
                        Log.i(TAG,"Message: "+statusMessage.getMessage().toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,""+"onComplete");
                    }
                });
    }

    /*public void getData5() {
        FBAddress fbAddress=new FBAddress();
        fbAddress.setFacebookid("");
        fbAddress.setAddress("TSovAsVLBJNJGMHMCJqsz3zDmXSZVgVAkS");



        Log.i(TAG,"userId"+userId+"  Address  "+Address);

        ApiService apiService=NetWorkManager.getApiService();
        Observable<StatusMessage> observable=apiService.getTalkUpload(fbAddress,null);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatusMessage>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StatusMessage balanceRet) {
                        Log.i(TAG,"Message: "+balanceRet.getMessage().toString());

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i(TAG,""+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,""+"onComplete");
                    }
                });
    }*/
}
