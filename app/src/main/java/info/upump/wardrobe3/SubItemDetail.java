package info.upump.wardrobe3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SubItemDetail extends AppCompatActivity implements View.OnClickListener, ViewDetailController {
    private EditText name;
    private EditText cost;
    private EditText description;
    private ImageView image;
    private Button cancelBtn, okBtn;
    public static final int EDIT =1;
    private long editId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_item_detail);
        name = (EditText) findViewById(R.id.detail_name);
        cost = (EditText) findViewById(R.id.detail_cost);
        description = (EditText) findViewById(R.id.detail_description);
        image = (ImageView) findViewById(R.id.detail_img);
        cancelBtn = (Button) findViewById(R.id.detail_btn_cancel);
        okBtn = (Button) findViewById(R.id.detail_btn_ok);
        cancelBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        Intent intent = getIntent();
        if(intent.getIntExtra("edit",0)>0){
            editId = intent.getLongExtra("id",0);
            name.setText(intent.getStringExtra("name"));
            System.out.println(intent.getStringExtra("cost"));
            cost.setText(intent.getStringExtra("cost"));
            //image
            description.setText(intent.getStringExtra("description"));

        }



    }

    @Override
    public void onClick(View v) {
        //TODO проверить на нулл
        switch (v.getId()) {
            case R.id.detail_btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.detail_btn_ok:
                Intent resultIntent = createResultIntent();
                setResult(RESULT_OK, resultIntent);
                finish();
                break;
        }

    }

    @Override
    public Intent createResultIntent() {
        Intent intent = new Intent();
        if(editId > 0){
            intent.putExtra("id",editId);
        }
        if (name.getText().toString().equals("")) {
            return null;
        }
        intent.putExtra("name", name.getText().toString());
        if(cost.getText().toString().equals("")){
            intent.putExtra("cost", 0);
        } else {
        intent.putExtra("cost", Float.parseFloat(cost.getText().toString()));}
        intent.putExtra("description", description.getText().toString());
        intent.putExtra("image","/ссылка");
            return intent;
    }
}
