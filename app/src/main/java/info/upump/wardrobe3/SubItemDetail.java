package info.upump.wardrobe3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import info.upump.wardrobe3.dialog.CameraOrChoosePhotoDialog;

public class SubItemDetail extends AppCompatActivity implements View.OnClickListener, ViewDetailController {
    public static final int CAMERA_RESULT = 2;
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

        image.setOnClickListener(this);
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
            case R.id.detail_img:
             /*   System.out.println("выбор картинки");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_RESULT);*/
                CameraOrChoosePhotoDialog cameraOrChoosePhotoDialog = new CameraOrChoosePhotoDialog();
                cameraOrChoosePhotoDialog.show(getFragmentManager(),"camera");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CameraOrChoosePhotoDialog cameraOrChoosePhotoDialog = (CameraOrChoosePhotoDialog) getFragmentManager().findFragmentByTag("camera");
        cameraOrChoosePhotoDialog.dismiss();
        if(data == null){return;}
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_RESULT){
                Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(thumbnailBitmap);

            }
        }
    }
}
