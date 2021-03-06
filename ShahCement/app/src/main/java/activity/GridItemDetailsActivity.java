package activity;

import android.arabin.shahcement.com.shahcement.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import utills.Constants;
import widget.ProgressHUD;

public class GridItemDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private String[] faq_pdf;
    private String[] lokkhoniyo_pdf;
    private String[] main_pdf;
    private int position;

    @BindView(R.id.pdf_viewer_id)
    PDFView pdfView;

    @BindView(R.id.tab_btn_lokkhoniyo_id)
    ImageView tab_btn_lokkhoniyo_id;

    @BindView(R.id.tab_btn_question_id)
    ImageView tab_btn_question_id;

    @BindView(R.id.tab_btn_video_id)
    ImageView tab_btn_video_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ProgressHUD mProgressHud = ProgressHUD.show(this, "LOADING ...", true);
        position = getIntent().getIntExtra(Constants.POSITION, 0);
        preparePdfString();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_grid_item_details);
        ButterKnife.bind( this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pdfView.fromAsset(main_pdf[position]).onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        if (mProgressHud.isShowing()) {
                                    overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                            pdfView.setVisibility(View.VISIBLE);
                            mProgressHud.dismiss();
                        }
                    }
                }).load();
            }
        },Constants.PROGRESS_DELAY);

        tab_btn_video_id.setOnClickListener(this);
        tab_btn_lokkhoniyo_id.setOnClickListener(this);
        tab_btn_question_id.setOnClickListener(this);
    }


    private void preparePdfString() {
        main_pdf = getResources().getStringArray(R.array.main_pdf_array);
        lokkhoniyo_pdf = getResources().getStringArray(R.array.lokkhoniyo_pdf);
        faq_pdf = getResources().getStringArray(R.array.faq_pdf);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_btn_lokkhoniyo_id:
                if (lokkhoniyo_pdf[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                    Toast.makeText(getApplicationContext(), Constants.FAQ_LOKH_TEXT, Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    lokkhonioClick(v);
                    break;
                }
            case R.id.tab_btn_question_id:
                if (faq_pdf[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                    Toast.makeText(getApplicationContext(), Constants.FAQ_LOKH_TEXT, Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    questionsClick(v);
                    break;
                }
            case R.id.tab_btn_video_id:
                Toast.makeText(getApplicationContext(), Constants.FAQ_LOKH_TEXT, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    private void questionsClick(View v) {
        Intent intent = new Intent(this, LokkhonioActivity.class);
        intent.putExtra(Constants.POSITION, this.position);
        intent.putExtra(Constants.TYPE, Constants.FAQ);
        intent.putExtra(Constants.LOKH_ARRAY, this.lokkhoniyo_pdf);
        intent.putExtra(Constants.FAQ_ARRAY, this.faq_pdf);
        startActivity(intent);
    }

    private void lokkhonioClick(View v) {
        Intent intent = new Intent(this, LokkhonioActivity.class);
        intent.putExtra(Constants.POSITION, this.position);
        intent.putExtra(Constants.TYPE, Constants.LOKH);
        intent.putExtra(Constants.LOKH_ARRAY, this.lokkhoniyo_pdf);
        intent.putExtra(Constants.FAQ_ARRAY, this.faq_pdf);
        startActivity(intent);
    }

    private void videoClick(View v) {
//        Intent intent = new Intent(this, VideoPlayerActivity.class);
//        intent.putExtra(Constants.POSITION, this.position);
//        intent.putExtra(Constants.ACTIVITY_STRING, 1001);
//        startActivity(intent);
    }

    public void onBackPressed() {
        finish();
    }

}
