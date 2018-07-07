package cats.match.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import cats.match.android.matchcats.R;
import cats.match.android.viewmodel.LoadingViewModel;

public class LoadingActivity extends AppCompatActivity {

    LoadingViewModel loadingViewModel = new LoadingViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadingViewModel.getPhotos();
    }
}
