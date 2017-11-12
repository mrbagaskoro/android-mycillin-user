package com.mycillin.user.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mycillin.user.R;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.accountDelete.ModelResultAccountDelete;
import com.mycillin.user.rest.accountInsert.ModelResultAccountInsert;
import com.mycillin.user.rest.accountUpdate.ModelResultAccountUpdate;
import com.mycillin.user.rest.relationList.ModelResultRelationList;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountDetailActivity extends AppCompatActivity {

    @BindView(R.id.accountDetailActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.accountDetailActivity_et_relationType)
    EditText relationTypeEdtxt;
    @BindView(R.id.accountDetailActivity_et_fullName)
    EditText fullNameEdtxt;
    @BindView(R.id.accountDetailActivity_et_address)
    EditText addressEdtxt;
    @BindView(R.id.accountDetailActivity_et_phone)
    EditText phoneEdtxt;
    @BindView(R.id.accountActivity_rg_genderRg)
    RadioGroup genderRGroup;
    @BindView(R.id.accountActivity_rb_genderMaleRb)
    RadioButton genderMaleRBtn;
    @BindView(R.id.accountActivity_rb_genderFemaleRb)
    RadioButton genderFemaleRBtn;
    @BindView(R.id.accountDetailActivity_et_dob)
    EditText dobEdtxt;
    @BindView(R.id.accountDetailActivity_et_height)
    EditText heightEdtxt;
    @BindView(R.id.accountDetailActivity_et_weight)
    EditText weightEdtxt;
    @BindView(R.id.accountDetailActivity_et_bloodType)
    EditText bloodTypeEdtxt;
    @BindView(R.id.accountDetailActivity_cb_isAgree)
    CheckBox isAgreeCBox;

    public static final String EXTRA_ACCOUNT_DETAIL = "EXTRA_ACCOUNT_DETAIL";
    public static final String EXTRA_ACCOUNT_DETAIL_IS_NEW = "EXTRA_ACCOUNT_DETAIL_IS_NEW";
    public static final String KEY_PARAM_ACCOUNT_NAME = "KEY_PARAM_ACCOUNT_NAME";
    public static final String KEY_PARAM_ACCOUNT_CREATED_BY = "KEY_PARAM_ACCOUNT_CREATED_BY";
    public static final String KEY_PARAM_ACCOUNT_CREATED_DATE = "KEY_PARAM_ACCOUNT_CREATED_DATE";
    public static final String KEY_PARAM_ACCOUNT_UPDATED_BY = "KEY_PARAM_ACCOUNT_UPDATED_BY";
    public static final String KEY_PARAM_ACCOUNT_UPDATED_DATE = "KEY_PARAM_ACCOUNT_UPDATED_DATE";
    public static final String KEY_PARAM_ACCOUNT_RELATION_ID = "KEY_PARAM_ACCOUNT_RELATION_ID";
    public static final String KEY_PARAM_ACCOUNT_RELATION_TYPE = "KEY_PARAM_ACCOUNT_RELATION_TYPE";
    public static final String KEY_PARAM_ACCOUNT_USER_ID = "KEY_PARAM_ACCOUNT_USER_ID";
    public static final String KEY_PARAM_ACCOUNT_GENDER = "KEY_PARAM_ACCOUNT_GENDER";
    public static final String KEY_PARAM_ACCOUNT_ADDRESS = "KEY_PARAM_ACCOUNT_ADDRESS";
    public static final String KEY_PARAM_ACCOUNT_MOBILE_NO = "KEY_PARAM_ACCOUNT_MOBILE_NO";
    public static final String KEY_PARAM_ACCOUNT_DOB = "KEY_PARAM_ACCOUNT_DOB";
    public static final String KEY_PARAM_ACCOUNT_HEIGHT = "KEY_PARAM_ACCOUNT_HEIGHT";
    public static final String KEY_PARAM_ACCOUNT_WEIGHT = "KEY_PARAM_ACCOUNT_WEIGHT";
    public static final String KEY_PARAM_ACCOUNT_BLOOD_TYPE = "KEY_PARAM_ACCOUNT_BLOOD_TYPE";
    public static final String KEY_PARAM_ACCOUNT_INSURANCE_ID = "KEY_PARAM_ACCOUNT_INSURANCE_ID";
    public static final String GENDER_VALUE_M = "M";
    public static final String GENDER_VALUE_F = "F";

    private boolean isNew = true;
    private HashMap<String, String> accountDetailData;
    private ArrayList<String> relationItems = new ArrayList<>();
    private HashMap<Integer, String> relationItemsTemp = new HashMap<>();
    private String selectedRelation;
    private ArrayList<String> bloodTypeItems = new ArrayList<>();

    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.accountDetailActivity_title);

        progressBarHandler = new ProgressBarHandler(this);

        isNew = getIntent().getBooleanExtra(EXTRA_ACCOUNT_DETAIL_IS_NEW, true);
        if(!isNew) {
            accountDetailData = (HashMap<String, String>) getIntent().getSerializableExtra(EXTRA_ACCOUNT_DETAIL);

            relationTypeEdtxt.setText(accountDetailData.get(KEY_PARAM_ACCOUNT_RELATION_TYPE));
            relationTypeEdtxt.setEnabled(false);
            relationTypeEdtxt.setBackgroundResource(R.drawable.disabled_edittext);
            relationTypeEdtxt.setTranslationY(5);

            fullNameEdtxt.setText(accountDetailData.get(KEY_PARAM_ACCOUNT_NAME));
            addressEdtxt.setText(accountDetailData.get(KEY_PARAM_ACCOUNT_ADDRESS));
            phoneEdtxt.setText(accountDetailData.get(KEY_PARAM_ACCOUNT_MOBILE_NO));
            if(accountDetailData.get(KEY_PARAM_ACCOUNT_GENDER).equals(GENDER_VALUE_M)) {
                genderRGroup.check(genderMaleRBtn.getId());
            }
            else if(accountDetailData.get(KEY_PARAM_ACCOUNT_GENDER).equals(GENDER_VALUE_F)) {
                genderRGroup.check(genderFemaleRBtn.getId());
            }
            dobEdtxt.setText(accountDetailData.get(KEY_PARAM_ACCOUNT_DOB));
            heightEdtxt.setText(accountDetailData.get(KEY_PARAM_ACCOUNT_HEIGHT));
            weightEdtxt.setText(accountDetailData.get(KEY_PARAM_ACCOUNT_WEIGHT));
            bloodTypeEdtxt.setText(accountDetailData.get(KEY_PARAM_ACCOUNT_BLOOD_TYPE));
            //isAgreeCBox.setText(params.get(KEY_PARAM_ACCOUNT_NAME));
        }

        final SpinnerDialog relationTypeSpinnerDialog = new SpinnerDialog(AccountDetailActivity.this, relationItems, getString(R.string.accountDetailActivity_relationTypeDropdownTitle), R.style.DialogAnimations_SmileWindow);
        relationTypeSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                relationTypeEdtxt.setText(s);
                for(int j = 0; j < relationItemsTemp.size(); j++) {
                    if(relationItemsTemp.get(j).split(" - ")[1].equals(s)) {
                        selectedRelation = relationItemsTemp.get(j).split(" - ")[0];
                    }
                }
            }
        });
        relationTypeEdtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRelationList(relationTypeSpinnerDialog);
            }
        });

        genderRGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                genderFemaleRBtn.setError(null);
            }
        });

        final SpinnerDialog bloodTypeSpinnerDialog = new SpinnerDialog(AccountDetailActivity.this, bloodTypeItems, getString(R.string.accountDetailActivity_bloodTypeDropdownTitle), R.style.DialogAnimations_SmileWindow);
        bloodTypeSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                bloodTypeEdtxt.setText(s);
            }
        });
        bloodTypeEdtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBloodTypeList(bloodTypeSpinnerDialog);
            }
        });

        dobEdtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(),
                        dateListener, 1999, 0, 1);
                datePickerDialog.show();*/

                DatePickerDialog datePickerDialog = new DatePickerDialog(AccountDetailActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dobEdtxt.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }

                }, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isNew) {
            getMenuInflater().inflate(R.menu.save_menu, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.save_delete_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            if(isAgreeCBox.isChecked()) {
                boolean isValid = true;

                if(relationTypeEdtxt.getText().toString().trim().equals("")) {
                    relationTypeEdtxt.setError(getString(R.string.accountDetailActivity_relationWarning));
                    isValid = false;
                }
                if(fullNameEdtxt.getText().toString().trim().equals("")) {
                    fullNameEdtxt.setError(getString(R.string.accountDetailActivity_fullNameWarning));
                    isValid = false;
                }
                if(addressEdtxt.getText().toString().trim().equals("")) {
                    addressEdtxt.setError(getString(R.string.accountDetailActivity_addressWarning));
                    isValid = false;
                }
                if(phoneEdtxt.getText().toString().trim().equals("")) {
                    phoneEdtxt.setError(getString(R.string.accountDetailActivity_phoneWarning));
                    isValid = false;
                }
                if(genderRGroup.getCheckedRadioButtonId() == -1) {
                    genderFemaleRBtn.setError(getString(R.string.accountDetailActivity_genderWarning));
                    isValid = false;
                }
                if(dobEdtxt.getText().toString().trim().equals("")) {
                    dobEdtxt.setError(getString(R.string.accountDetailActivity_dobWarning));
                    isValid = false;
                }
                if(heightEdtxt.getText().toString().trim().equals("")) {
                    heightEdtxt.setError(getString(R.string.accountDetailActivity_heightWarning));
                    isValid = false;
                }
                if(weightEdtxt.getText().toString().trim().equals("")) {
                    weightEdtxt.setError(getString(R.string.accountDetailActivity_weightWarning));
                    isValid = false;
                }
                if(bloodTypeEdtxt.getText().toString().trim().equals("")) {
                    bloodTypeEdtxt.setError(getString(R.string.accountDetailActivity_bloodTypeWarning));
                    isValid = false;
                }

                if(isValid) {
                    new AlertDialog.Builder(AccountDetailActivity.this)
                            .setTitle(getString(R.string.menu_save))
                            .setMessage(R.string.accountDetailActivity_saveMessage)
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton(getString(R.string.menu_save), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(isNew) {
                                        doInsert();
                                    }
                                    else {
                                        doUpdate(accountDetailData.get(KEY_PARAM_ACCOUNT_RELATION_ID));
                                    }
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
            }
            else {
                Snackbar.make(getWindow().getDecorView().getRootView(), R.string.accountDetailActivity_agreementWarning, Snackbar.LENGTH_SHORT).show();
            }
            return true;
        }

        if(!isNew) {
            if(id == R.id.action_delete) {
                if(accountDetailData.get(KEY_PARAM_ACCOUNT_RELATION_TYPE).equals("01")) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), R.string.accountDetailActivity_deleteMainAccWarning, Snackbar.LENGTH_SHORT).show();
                }
                else {
                    new AlertDialog.Builder(AccountDetailActivity.this)
                            .setTitle(R.string.accountActivityDetail_deleteTitle)
                            .setMessage(R.string.accountActivityDetail_deleteMessage)
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton(getString(R.string.accountActivityDetail_deleteTitle), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    doDelete(accountDetailData.get(KEY_PARAM_ACCOUNT_RELATION_ID));
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

            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void doUpdate(String relationId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("relation_id", relationId);
        params.put("full_name", fullNameEdtxt.getText().toString());
        params.put("address", addressEdtxt.getText().toString());
        params.put("mobile_number", phoneEdtxt.getText().toString());
        if(genderRGroup.getCheckedRadioButtonId() == genderRGroup.getChildAt(0).getId()) {
            params.put("gender", GENDER_VALUE_M);
        }
        else if(genderRGroup.getCheckedRadioButtonId() == genderRGroup.getChildAt(1).getId()) {
            params.put("gender", GENDER_VALUE_F);
        }
        params.put("dob", dobEdtxt.getText().toString());
        params.put("height", heightEdtxt.getText().toString());
        params.put("weight", weightEdtxt.getText().toString());
        params.put("blood_type", bloodTypeEdtxt.getText().toString());
        params.put("insurance_id", "");

        myCillinAPI.doUpdateAccount(token, params).enqueue(new Callback<ModelResultAccountUpdate>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultAccountUpdate> call, @NonNull Response<ModelResultAccountUpdate> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultAccountUpdate modelResultAccountUpdate = response.body();

                    assert modelResultAccountUpdate != null;
                    if(modelResultAccountUpdate.getResult().isStatus()) {
                        String message = modelResultAccountUpdate.getResult().getMessage();
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT)
                                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);
                                        finish();
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
            public void onFailure(@NonNull Call<ModelResultAccountUpdate> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void doInsert() {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("relation_type", selectedRelation);
        params.put("full_name", fullNameEdtxt.getText().toString());
        params.put("address", addressEdtxt.getText().toString());
        params.put("mobile_number", phoneEdtxt.getText().toString());
        if(genderRGroup.getCheckedRadioButtonId() == genderRGroup.getChildAt(0).getId()) {
            params.put("gender", GENDER_VALUE_M);
        }
        else if(genderRGroup.getCheckedRadioButtonId() == genderRGroup.getChildAt(1).getId()) {
            params.put("gender", GENDER_VALUE_F);
        }
        params.put("dob", dobEdtxt.getText().toString());
        params.put("height", heightEdtxt.getText().toString());
        params.put("weight", weightEdtxt.getText().toString());
        params.put("blood_type", bloodTypeEdtxt.getText().toString());
        params.put("insurance_id", "");

        myCillinAPI.doInsertAccount(token, params).enqueue(new Callback<ModelResultAccountInsert>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultAccountInsert> call, @NonNull Response<ModelResultAccountInsert> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultAccountInsert modelResultAccountInsert = response.body();

                    assert modelResultAccountInsert != null;
                    if(modelResultAccountInsert.getResult().isStatus()) {
                        String message = modelResultAccountInsert.getResult().getMessage();
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT)
                                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);
                                        finish();
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
            public void onFailure(@NonNull Call<ModelResultAccountInsert> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void doDelete(String relationId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("relation_id", Integer.parseInt(relationId));

        myCillinAPI.doDeleteAccount(token, params).enqueue(new Callback<ModelResultAccountDelete>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultAccountDelete> call, @NonNull Response<ModelResultAccountDelete> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultAccountDelete modelResultAccountDelete = response.body();

                    assert modelResultAccountDelete != null;
                    if(modelResultAccountDelete.getResult().isStatus()) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.accountDetailActivity_deleteSuccessMessage, Snackbar.LENGTH_SHORT)
                                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);
                                        finish();
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
            public void onFailure(@NonNull Call<ModelResultAccountDelete> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getRelationList(final SpinnerDialog spinnerDialog) {
        progressBarHandler.show();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();
        myCillinAPI.getRelationList().enqueue(new Callback<ModelResultRelationList>() {
            @Override
            public void onResponse(Call<ModelResultRelationList> call, Response<ModelResultRelationList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultRelationList modelResultRelationList = response.body();

                    assert modelResultRelationList != null;
                    if(modelResultRelationList.getResult().isStatus()) {

                        relationItems.clear();
                        relationItemsTemp.clear();
                        int size = modelResultRelationList.getResult().getData().size();
                        for(int i = 0; i < size; i++) {
                            String relationId = modelResultRelationList.getResult().getData().get(i).getRelationId();
                            String relationDesc = modelResultRelationList.getResult().getData().get(i).getRelationDesc();

                            relationItems.add(relationDesc);
                            relationItemsTemp.put(i, relationId + " - " + relationDesc);
                        }
                        spinnerDialog.showSpinerDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelResultRelationList> call, Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getBloodTypeList(SpinnerDialog spinnerDialog) {
        bloodTypeItems.clear();
        bloodTypeItems.add("A");
        bloodTypeItems.add("B");
        bloodTypeItems.add("AB");
        bloodTypeItems.add("O");

        spinnerDialog.showSpinerDialog();
    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            dobEdtxt.setText(arg1 + "-" + (arg2 + 1) + "-" + arg3);
        }
    };
}
