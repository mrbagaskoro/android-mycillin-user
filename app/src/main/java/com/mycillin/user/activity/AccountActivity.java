package com.mycillin.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.mycillin.user.R;
import com.mycillin.user.adapter.AccountAdapter;
import com.mycillin.user.adapter.DialogImagePickerAdapter;
import com.mycillin.user.list.AccountList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.accountList.ModelResultAccountList;
import com.mycillin.user.rest.accountPicGet.ModelResultAccountPicGet;
import com.mycillin.user.rest.accountPicUpdate.ModelResultAccountPicUpdate;
import com.mycillin.user.rest.insuranceInsert.ModelResultInsuranceInsert;
import com.mycillin.user.rest.login.ModelResultLogin;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.RecyclerTouchListener;
import com.mycillin.user.util.SessionManager;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.otaliastudios.cameraview.CameraUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.accountActivity_iv_userAvatar)
    CircleImageView userAvatar;
    @BindView(R.id.accountActivity_iv_editPicture)
    ImageView editPictureBtn;
    @BindView(R.id.accountActivity_ll_manageAccount)
    LinearLayout manageAccount;
    @BindView(R.id.accountActivity_ll_manageInsurance)
    LinearLayout manageInsurance;
    @BindView(R.id.accountActivity_ll_managePaymentMethod)
    LinearLayout managePaymentMethod;
    @BindView(R.id.accountActivity_ll_changePassword)
    LinearLayout changePassword;
    @BindView(R.id.accountActivity_ll_changePIN)
    LinearLayout changePIN;
    @BindView(R.id.accountActivity_ll_signOut)
    LinearLayout signOut;
    @BindView(R.id.accountActivity_ll_termsPrivacyPolicy)
    LinearLayout termsAndPrivacyPolicy;

    @BindView(R.id.accountActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.accountActivity_tv_userName)
    TextView patientsNameTxt;

    RecyclerView accountRecyclerView;

    private List<AccountList> accountLists = new ArrayList<>();
    private AccountAdapter accountAdapter;

    private ProgressBarHandler progressBarHandler;

    public static final String KEY_MANAGE_ACCOUNT = "KEY_MANAGE_ACCOUNT";
    public static final String KEY_MANAGE_INSURANCE = "KEY_MANAGE_INSURANCE";
    public static final String KEY_MANAGE_PAYMENT_METHOD = "KEY_MANAGE_PAYMENT_METHOD";
    public static final int REQUEST_CODE_GALLERY = 1002;
    public static final int REQUEST_CODE_CAMERA = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.nav_account);

        final SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        patientsNameTxt.setText(sessionManager.getUserFullName());

        progressBarHandler = new ProgressBarHandler(this);

        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sessionManager.getUserPicUrl().isEmpty()) {
                    Intent intent = new Intent(AccountActivity.this, ViewImageActivity.class);
                    intent.putExtra(ViewImageActivity.EXTRA_IMAGE_URL, sessionManager.getUserPicUrl());
                    startActivity(intent);
                }
            }
        });

        editPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerDialog();
            }
        });

        manageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showManageAccountDialog(KEY_MANAGE_ACCOUNT);
            }
        });

        manageInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showManageAccountDialog(KEY_MANAGE_INSURANCE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showChangePasswordDialog();
                Intent intent = new Intent(AccountActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        changePIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, ChangePINActivity.class);
                startActivity(intent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSignOut();
            }
        });

        termsAndPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, TermsAndPrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.getUserPicUrl().equals("")) {
            getUserPic();
        }
        else {
            loadAccountPic(sessionManager.getUserPicUrl());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_CODE_GALLERY) {
                try {
                    Uri selectedImage = data.getData();
                    Bitmap bitmapFromGallery = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    updateAccountPic(bitmapFromGallery);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode == REQUEST_CODE_CAMERA) {
                Bitmap bitmapFromCamera = (Bitmap) data.getExtras().get("data");
                updateAccountPic(bitmapFromCamera);
            }
        }
    }

    private void showManageAccountDialog(String id) {
        final DialogPlus dialogPlus = DialogPlus.newDialog(AccountActivity.this)
                .setContentHolder(new ViewHolder(R.layout.dialog_manage_accounts_layout))
                .setGravity(Gravity.CENTER)
                .create();
        dialogPlus.show();

        View dialogPlusView = dialogPlus.getHolderView();

        getAccountList(dialogPlus, dialogPlusView, id);

        ImageButton addAccountBtn = dialogPlusView.findViewById(R.id.manageAccountDialog_ib_addAccountBtn);
        if(id.equals(KEY_MANAGE_ACCOUNT)) {
            addAccountBtn.setVisibility(View.VISIBLE);
            addAccountBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AccountActivity.this, AccountDetailActivity.class);
                    intent.putExtra(AccountDetailActivity.EXTRA_ACCOUNT_DETAIL_IS_NEW, true);
                    startActivity(intent);
                    dialogPlus.dismiss();
                }
            });
        }
        else {
            addAccountBtn.setVisibility(View.GONE);
        }
    }

    private void doSignOut() {
        new AlertDialog.Builder(AccountActivity.this)
                .setTitle(getString(R.string.accountActivity_signOutTitle))
                .setMessage(R.string.accountActivity_signOutMessage)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(getString(R.string.accountActivity_signOutTitle), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SessionManager session = new SessionManager(getApplicationContext());
                        session.logoutUser();
                        finish();
                    }
                })
                .setNegativeButton(R.string.accountActivity_cancelTitle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void getAccountList(final DialogPlus dialogPlus, final View view, final String id) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);

        myCillinAPI.getAccountList(token, params).enqueue(new Callback<ModelResultAccountList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultAccountList> call, @NonNull Response<ModelResultAccountList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultAccountList modelResultAccountList = response.body();

                    assert modelResultAccountList != null;
                    if(modelResultAccountList.getResult().isStatus()) {
                        accountRecyclerView = view.findViewById(R.id.manageAccountDialog_rv_recyclertView);
                        accountRecyclerView.setLayoutManager(new LinearLayoutManager(AccountActivity.this));
                        accountRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        accountLists.clear();

                        int size = modelResultAccountList.getResult().getData().size();
                        for(int i = 0; i < size; i++) {
                            String accountName = modelResultAccountList.getResult().getData().get(i).getFullName();
                            String accountCreatedBy = modelResultAccountList.getResult().getData().get(i).getCreatedBy();
                            String accountCreatedDate = modelResultAccountList.getResult().getData().get(i).getCreatedDate();
                            String accountUpdatedBy = modelResultAccountList.getResult().getData().get(i).getUpdatedBy();
                            String accountUpdatedDate = modelResultAccountList.getResult().getData().get(i).getUpdatedDate();
                            String accountRelationId = modelResultAccountList.getResult().getData().get(i).getRelationId();
                            String accountRelationType = modelResultAccountList.getResult().getData().get(i).getRelationType();
                            String accountUserId = modelResultAccountList.getResult().getData().get(i).getUserId();
                            String accountGender = modelResultAccountList.getResult().getData().get(i).getGender();
                            String accountAddress = modelResultAccountList.getResult().getData().get(i).getAddress();
                            String accountMobileNo = modelResultAccountList.getResult().getData().get(i).getMobileNo();
                            String accountDOB = modelResultAccountList.getResult().getData().get(i).getDob();
                            String accountHeight = modelResultAccountList.getResult().getData().get(i).getHeight();
                            String accountWeight = modelResultAccountList.getResult().getData().get(i).getWeight();
                            String accountBloodType = modelResultAccountList.getResult().getData().get(i).getBloodType();
                            String accountInsuranceId = modelResultAccountList.getResult().getData().get(i).getInsuranceId();

                            accountLists.add(new AccountList(accountName,
                                    accountCreatedBy, accountCreatedDate, accountUpdatedBy,
                                    accountUpdatedDate, accountRelationId, accountRelationType,
                                    accountUserId, accountGender, accountAddress, accountMobileNo,
                                    accountDOB, accountHeight, accountWeight, accountBloodType,
                                    accountInsuranceId));
                        }

                        accountAdapter = new AccountAdapter(accountLists);
                        accountRecyclerView.setAdapter(accountAdapter);
                        accountAdapter.notifyDataSetChanged();

                        accountRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), accountRecyclerView, new RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                AccountList list = accountLists.get(position);

                                HashMap<String, String> params = new HashMap<>();
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_NAME, list.getAccountName());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_CREATED_BY, list.getAccountCreatedBy());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_CREATED_DATE, list.getAccountCreatedDate());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_UPDATED_BY, list.getAccountUpdatedBy());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_UPDATED_DATE, list.getAccountUpdatedDate());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_RELATION_ID, list.getAccountRelationId());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_RELATION_TYPE, list.getAccountRelationType());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_USER_ID, list.getAccountUserId());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_GENDER, list.getAccountGender() == null ? "" : list.getAccountGender());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_ADDRESS, list.getAccountAddress());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_MOBILE_NO, list.getAccountMobileNo());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_DOB, list.getAccountDOB());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_HEIGHT, list.getAccountHeight());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_WEIGHT, list.getAccountWeight());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_BLOOD_TYPE, list.getAccountBloodType());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_INSURANCE_ID, list.getAccountInsuranceId());

                                if(id.equals(KEY_MANAGE_ACCOUNT)) {
                                    Intent intent = new Intent(AccountActivity.this, AccountDetailActivity.class);
                                    intent.putExtra(AccountDetailActivity.EXTRA_ACCOUNT_DETAIL, params);
                                    intent.putExtra(AccountDetailActivity.EXTRA_ACCOUNT_DETAIL_IS_NEW, false);
                                    startActivity(intent);

                                }
                                else {
                                    Intent intent = new Intent(AccountActivity.this, InsuranceListActivity.class);
                                    intent.putExtra(InsuranceListActivity.KEY_PARAM_ACCOUNT_USER_ID, list.getAccountUserId());
                                    intent.putExtra(InsuranceListActivity.KEY_PARAM_ACCOUNT_RELATION_ID, list.getAccountRelationId());
                                    startActivity(intent);
                                }
                                dialogPlus.dismiss();
                            }

                            @Override
                            public void onLongClick(View view, int position) {

                            }
                        }));
                    }
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if(jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        }
                        else {

                            message = jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultAccountList> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void showImagePickerDialog() {
        DialogImagePickerAdapter dialogImagePickerAdapter = new DialogImagePickerAdapter(AccountActivity.this);
        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new GridHolder(2))
                .setHeader(R.layout.dialog_image_picker_header)
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .setAdapter(dialogImagePickerAdapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        if(position == 0) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto , REQUEST_CODE_GALLERY);
                            dialog.dismiss();
                        }
                        else if(position == 1) {
                            Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, REQUEST_CODE_CAMERA);
                            dialog.dismiss();
                        }
                    }
                })
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        dialog.show();
    }

    private void getUserPic() {
        progressBarHandler.show();

        final SessionManager session = new SessionManager(getApplicationContext());
        String token = session.getUserToken();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", session.getUserId());

        myCillinAPI.getAvatar(token, params).enqueue(new Callback<ModelResultAccountPicGet>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultAccountPicGet> call, @NonNull Response<ModelResultAccountPicGet> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultAccountPicGet modelResultAccountPicGet = response.body();
                    assert modelResultAccountPicGet != null;

                    if(modelResultAccountPicGet.getResult().isStatus()) {
                        loadAccountPic(modelResultAccountPicGet.getResult().getMessage().get(0).getImageProfile());
                        session.setUserPicUrl(modelResultAccountPicGet.getResult().getMessage().get(0).getImageProfile());
                    }
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if(jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        }
                        else {
                            message = "FUCK!!!" + jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultAccountPicGet> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAccountPic(String url) {
        if(!url.isEmpty()) {

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_action_user)
                    .fitCenter();

            Glide.with(getApplicationContext())
                    .load(url)
                    .apply(requestOptions)
                    .into(userAvatar);
        }
    }

    private void updateAccountPic(final Bitmap bitmap) {
        try {
            progressBarHandler.show();
            final SessionManager sessionManager = new SessionManager(getApplicationContext());
            String token = sessionManager.getUserToken();
            MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

            String fileName = "img_" + sessionManager.getUserId();

            File file = new File(getApplicationContext().getFilesDir(), fileName + ".jpg");

            OutputStream os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getUserId());
            RequestBody profileImgFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part profileImg = MultipartBody.Part.createFormData("profile_img", file.getName(), profileImgFile);

            myCillinAPI.updateAccountPic(token, userId, profileImg).enqueue(new Callback<ModelResultAccountPicUpdate>() {
                        @Override
                        public void onResponse(@NonNull Call<ModelResultAccountPicUpdate> call, @NonNull Response<ModelResultAccountPicUpdate> response) {
                            progressBarHandler.hide();

                            if(response.isSuccessful()) {
                                final ModelResultAccountPicUpdate modelResultAccountPicUpdate = response.body();

                                assert modelResultAccountPicUpdate != null;
                                if(modelResultAccountPicUpdate.getResult().isStatus()) {
                                    Snackbar.make(getWindow().getDecorView().getRootView(), R.string.AccountActivity_updatePicSuccessMessage, Snackbar.LENGTH_SHORT)
                                            .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                                @Override
                                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                                    super.onDismissed(transientBottomBar, event);

                                                    String imageProfileUrl = modelResultAccountPicUpdate.getResult().getMessage().get(0).getImageProfile();
                                                    sessionManager.setUserPicUrl(imageProfileUrl);
                                                    loadAccountPic(imageProfileUrl);
                                                    userAvatar.setImageBitmap(bitmap);
                                                }
                                            })
                                            .show();
                                }
                            }
                            else {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                    String message;
                                    if(jsonObject.has("result")) {
                                        message = jsonObject.getJSONObject("result").getString("message");
                                    }
                                    else {

                                        message = jsonObject.getString("message");
                                    }
                                    Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ModelResultAccountPicUpdate> call, @NonNull Throwable t) {
                            // TODO: 12/10/2017 SET FAILURE SCENARIO
                            progressBarHandler.hide();
                            Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
