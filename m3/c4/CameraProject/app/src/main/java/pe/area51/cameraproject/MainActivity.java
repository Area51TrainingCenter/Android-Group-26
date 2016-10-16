package pe.area51.cameraproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageView previewImageView;

    private File currentPhotoFile;

    public static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.previewImageView = (ImageView) findViewById(R.id.activity_main_imageview_preview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_take_photo) {
            dispatchTakePhotoIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            addPictureToGallery(this, currentPhotoFile);
            setPicture(currentPhotoFile, previewImageView);
        }
    }

    private static Bitmap setPicture(final File pictureFile, final ImageView imageView) {
        final int imageViewWidth = imageView.getWidth();
        final int imageViewHeight = imageView.getHeight();

        final BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
        bitmapFactoryOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(pictureFile.getPath(), bitmapFactoryOptions);

        final int bitmapWidth = bitmapFactoryOptions.outWidth;
        final int bitmapHeight = bitmapFactoryOptions.outHeight;

        final int scaleFactor = Math.min(bitmapWidth / imageViewWidth, bitmapHeight / imageViewHeight);

        bitmapFactoryOptions.inJustDecodeBounds = false;
        bitmapFactoryOptions.inSampleSize = scaleFactor;
        bitmapFactoryOptions.inPurgeable = true;

        final Bitmap decodedBitmap = BitmapFactory.decodeFile(pictureFile.getPath(), bitmapFactoryOptions);

        imageView.setImageBitmap(decodedBitmap);

        return decodedBitmap;
    }

    private static File createTempImageFile() throws IOException {
        final String fileName = "picture_" + System.currentTimeMillis();
        final File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        return File.createTempFile(fileName, ".jpg", storageDir);
    }

    private static void addPictureToGallery(final Context context, final File pictureFile) {
        context.sendBroadcast(
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                        .setData(Uri.fromFile(pictureFile))
        );
    }

    private void dispatchTakePhotoIntent() {
        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (isActivityAvailable(this, takePictureIntent)) {
            try {
                this.currentPhotoFile = createTempImageFile();
                startActivityForResult(
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(this.currentPhotoFile)),
                        REQUEST_TAKE_PHOTO);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            MessageDialogFragment.newInstance(
                    getString(R.string.error),
                    getString(R.string.no_camera_app)
            ).show(getSupportFragmentManager(), "alert_dialog");
        }
    }

    private static boolean isActivityAvailable(final Context context, final Intent intent) {
        return intent.resolveActivity(context.getPackageManager()) != null;
    }

    public static class MessageDialogFragment extends DialogFragment {

        private static final String ARG_TITLE = "title";
        private static final String ARG_MESSAGE = "message";

        public static MessageDialogFragment newInstance(final String title, final String message) {
            final MessageDialogFragment fragment = new MessageDialogFragment();
            final Bundle args = new Bundle();
            args.putString(ARG_TITLE, title);
            args.putString(ARG_MESSAGE, message);
            fragment.setArguments(args);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final String title = getArguments().getString(ARG_TITLE, "");
            final String message = getArguments().getString(ARG_MESSAGE, "");
            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        }
    }
}
