package info.upump.wardrobe3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.List;

import info.upump.wardrobe3.dialog.CameraOrChoosePhotoDialog;

import static android.support.v4.content.FileProvider.getUriForFile;

public class SubItemDetail extends AppCompatActivity implements View.OnClickListener, ViewDetailController {
    public static final int CAMERA_RESULT = 2;
    public static final int CHOOSE_PHOTO_RESULT = 3;
    private EditText name;
    private EditText cost;
    private EditText description;
    private ImageView image;
    private Button cancelBtn, okBtn;
    public static final int EDIT = 1;
    private long editId;
    private long idParent;
    private File fileFromDialog;
    private Uri uriFromDialog;

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
        idParent = intent.getLongExtra("id", 0);
        if (intent.getIntExtra("edit", 0) > 0) {
            editId = intent.getLongExtra("id", 0);
            name.setText(intent.getStringExtra("name"));
            System.out.println(intent.getStringExtra("cost"));
            cost.setText(intent.getStringExtra("cost"));
            //image.setImageURI(getUriFromDB(intent.getStringExtra("image")));
            try {
                image.setImageURI(Uri.parse(intent.getStringExtra("image")));
            }catch (NullPointerException e){

            }
                description.setText(intent.getStringExtra("description"));
        }
    }
/*

    private Uri getUriFromDB(String image) {
        System.out.println(image);
        File directory = new File(getFilesDir(), "images");
        File file = new File(directory.getPath(), "/" + image);
        Uri uri = getUriForFile(this, "info.upump.wardrobe3.fileprovider", file);
        System.out.println(uri);
        return uri;
    }
*/

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
                CameraOrChoosePhotoDialog cameraOrChoosePhotoDialog = new CameraOrChoosePhotoDialog();
                cameraOrChoosePhotoDialog.show(getFragmentManager(), "camera");
                break;
        }

    }

    @Override
    public Intent createResultIntent() {
        Intent intent = new Intent();
        if (editId > 0) {
            intent.putExtra("id", editId);
        }
        if (name.getText().toString().equals("")) {
            return null;
        }
        intent.putExtra("name", name.getText().toString());
        if (cost.getText().toString().equals("")) {
            intent.putExtra("cost", 0);
        } else {
            intent.putExtra("cost", Float.parseFloat(cost.getText().toString()));
        }
        intent.putExtra("idParent", idParent);
        intent.putExtra("description", description.getText().toString());
     /*   if (fileFromDialog != null) {
            intent.putExtra("image", Uri.fromFile(fileFromDialog));
        }*/
        if (uriFromDialog != null) {
            intent.putExtra("image",uriFromDialog.toString());
        }
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CameraOrChoosePhotoDialog cameraOrChoosePhotoDialog = (CameraOrChoosePhotoDialog) getFragmentManager().findFragmentByTag("camera");
        System.out.println(resultCode);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_RESULT) {
                fileFromDialog = cameraOrChoosePhotoDialog.getFile();
                uriFromDialog = cameraOrChoosePhotoDialog.getUri();
                // TODO проверка на размер экрана
                image.setImageURI(uriFromDialog);
                addPicToGallery();
                System.out.println("ur " + uriFromDialog.toString());
            }
            if (requestCode == CHOOSE_PHOTO_RESULT) {
                System.out.println(data.getData());
                uriFromDialog = data.getData();
                image.setImageURI(uriFromDialog);

            }
        }
        cameraOrChoosePhotoDialog.dismiss();
    }

    private void addPicToGallery() {
        System.out.println("ur2" + uriFromDialog);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(mediaScanIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            System.out.println("package name" +packageName);
            this.grantUriPermission(packageName, uriFromDialog, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

           mediaScanIntent.setData(uriFromDialog);
           sendBroadcast(mediaScanIntent);
           System.out.println("121313123");
    }

}
