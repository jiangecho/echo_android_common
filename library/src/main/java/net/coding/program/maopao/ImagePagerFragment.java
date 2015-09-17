package net.coding.program.maopao;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import cn.pocdoc.callme.network.HttpUtil;
import cn.pocdoc.callme.utils.FileUtil;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import java.io.File;

/**
 * Created by chaochen on 14-9-7.
 */
@EFragment(R.layout.activity_image_pager_item_mp)
public class ImagePagerFragment extends Fragment {

    private String URL_FILES = "";

    @ViewById
    DonutProgress circleLoading;

    @ViewById
    View imageLoadFail;

    @ViewById
    ViewGroup rootLayout;

    View image;

    public void setData(String uriString) {
        uri = uriString;
    }

    File mFile;

    @FragmentArg
    String uri;

    @FragmentArg
    String fileId;

    @FragmentArg
    int mProjectObjectId;

    public void setData(String fileId, int mProjectObjectId) {
        this.fileId = fileId;
        this.mProjectObjectId = mProjectObjectId;
    }

//    public static DisplayImageOptions optionsImage = new DisplayImageOptions
//            .Builder()
//            .showImageForEmptyUri(R.drawable.image_not_exist)
//            .showImageOnFail(R.drawable.image_not_exist)
//            .bitmapConfig(Bitmap.Config.RGB_565)
//            .cacheOnDisk(true)
//            .resetViewBeforeLoading(true)
//            .cacheInMemory(false)
//            .considerExifParams(true)
//            .imageScaleType(ImageScaleType.EXACTLY)
//            .build();

    @AfterViews
    void init() {
        circleLoading.setVisibility(View.INVISIBLE);
        if (uri == null) {
        } else {
            showPhoto(false);
        }
    }

    @Override
    public void onDestroyView() {
        if (image != null) {
//            if (image instanceof GifImageView) {
//                ((GifImageView) image).setImageURI(null);
//            } else
            if (image instanceof PhotoView) {
                try {
                    Bitmap bitmap = ((BitmapDrawable) ((PhotoView) image).getDrawable()).getBitmap();
                    ((PhotoView) image).setImageDrawable(null);
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    //((BitmapDrawable) ((PhotoView) image).getDrawable()).getBitmap().recycle();
                } catch (Exception e) {
                }
            }
        }

        super.onDestroyView();
    }

    private void showPhoto(boolean isGif) {
        if (!isAdded()) {
            return;
        }

        if (isGif) {
//            GifImageView gifView = (GifImageView) getActivity().getLayoutInflater().inflate(R.layout.imageview_gif_mp, null);
//            image = gifView;
//            rootLayout.addView(image);
//
//            image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getActivity().onBackPressed();
//                }
//            });

        } else {
            PhotoView photoView = (PhotoView) getActivity().getLayoutInflater().inflate(R.layout.imageview_touch_mp, null);
            image = photoView;
            rootLayout.addView(image);

            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v2) {
                    getActivity().onBackPressed();
                }
            });
        }

        ImageSize size = new ImageSize(MyApp.sWidthPix, MyApp.sHeightPix);
        ImageLoader.getInstance().loadImage(uri, size, MyApp.displayImageOptions, new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        circleLoading.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        if (!isAdded()) {
                            return;
                        }

//                        String message;
//                        switch (failReason.getType()) {
//                            case IO_ERROR:
//                                message = "IO错误";
//                                break;
//                            case DECODING_ERROR:
//                                message = "图片编码错误";
//                                break;
//                            case NETWORK_DENIED:
//                                message = "载入图片超时";
//                                break;
//                            case OUT_OF_MEMORY:
//                                message = "内存不足";
//                                break;
//                            case UNKNOWN:
//                                message = "未知错误";
//                                break;
//                            default:
//                                message = "未知错误";
//                                break;
//                        }
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        circleLoading.setVisibility(View.GONE);
                        imageLoadFail.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingComplete(final String imageUri, View view, Bitmap loadedImage) {
                        if (!isAdded()) {
                            return;
                        }
                        circleLoading.setVisibility(View.GONE);

                        image.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                /*
                                new AlertDialog.Builder(getActivity())
                                        .setItems(new String[]{"保存到手机"}, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (which == 0) {
                                                    if (client == null) {
                                                        client = HttpUtil.getClient();
                                                        client.get(getActivity(), imageUri, new FileAsyncHttpResponseHandler(mFile) {

                                                            @Override
                                                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                                                                client = null;
                                                                //showButtomToast("保存失败");
                                                                Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
                                                            }

                                                            @Override
                                                            public void onSuccess(int statusCode, Header[] headers, File file) {
                                                                client = null;
                                                                //showButtomToast("图片已保存到:" + file.getPath());
                                                                Toast.makeText(getActivity(), "图片已保存到：" + file.getPath(), Toast.LENGTH_SHORT).show();
                                                                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                                                            }
                                                        });
                                                    }

                                                }
                                            }
                                        })
                                        .show();

                                */
                                return true;
                            }
                        });

//                        if (image instanceof GifImageView) {
//                            File file = getImageLoad().imageLoader.getDiskCache().get(imageUri);
//                            if (file.exists()) {
//                                Log.d("", "yyy");
//                            } else {
//                                Log.d("", "nnn");
//                            }
//
//                            // 看umeng有报错，bad file descriptor，可能有些gif有问题
//                            try {
//                                Uri uri1 = Uri.fromFile(file);
//                                ((GifImageView) image).setImageURI(uri1);
////                                ((GifImageView) image).setImageURI(file.);
//                            } catch (Exception e) {
//                                Global.errorLog(e);
//                            }
////                        } else if (image instanceof SubsamplingScaleImageView) {
////                            File file = getImageLoad().imageLoader.getDiskCache().get(imageUri);
////
////                            try {
////                                ((SubsamplingScaleImageView) image).setImageUri(Uri.fromFile(file));
////                            } catch (Exception e) {
////                                Global.errorLog(e);
////                            }
////                        }
//                        } else
                        if (image instanceof PhotoView) {
                            try {
//                                ((ImageViewTouch) image).setImageBitmap(loadedImage, matrix, 0.5f, 2.0f);
                                ((PhotoView) image).setImageBitmap(loadedImage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                },
                new ImageLoadingProgressListener() {

                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        if (!isAdded()) {
                            return;
                        }

                        int progress = current * 100 / total;
                        circleLoading.setProgress(progress);
                    }
                });

        //mFile = FileUtil.getDestinationInExternalPublicDir(getFileDownloadPath(), uri.replaceAll(".*/(.*?)", "$1"));
    }


    private AsyncHttpClient client;

    @Override
    public void onDestroy() {
        if (client != null) {
            client.cancelRequests(getActivity(), true);
            client = null;
        }

        super.onDestroy();
    }

    public String getFileDownloadPath() {
//        String path;
//        String defaultPath = Environment.DIRECTORY_DOWNLOADS + File.separator + FileUtil.DOWNLOAD_FOLDER;
//        SharedPreferences share = getActivity().getSharedPreferences(FileUtil.DOWNLOAD_SETTING, Context.MODE_PRIVATE);
//        if (share.contains(FileUtil.DOWNLOAD_PATH)) {
//            path = share.getString(FileUtil.DOWNLOAD_PATH, Environment.DIRECTORY_DOWNLOADS + File.separator + FileUtil.DOWNLOAD_FOLDER);
//        } else {
//            path = defaultPath;
//        }
//        return path;
        return null;
    }
}
