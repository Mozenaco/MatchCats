package cats.match.android.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cats.match.android.data.entities.HighScore;
import cats.match.android.matchcats.R;

/**
 * Adapter for the list of HighScores
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
public class HighScoresAdapter extends RecyclerView.Adapter<HighScoresAdapter.ViewHolder> {

    private final List<HighScore> mValues;

    public HighScoresAdapter(List<HighScore> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.highscores_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        HighScore highScore = mValues.get(position);

        holder.tvPlayerName.setText(highScore.getName());
        holder.tvPlayerScore.setText(highScore.getScoreAsString());
        //[S]ingle Responsibility - A class should have only one reason to change

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlayerName;
        TextView tvPlayerScore;

        ViewHolder(View view) {
            super(view);

            tvPlayerName = view.findViewById(R.id.tvPlayerName);
            tvPlayerScore = view.findViewById(R.id.tvPlayerScore);
        }
    }
}