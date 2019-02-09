package com.example.android.lab01_2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class NewsFragment extends Fragment
{
    static final String ARG_POSITION = "position";

    int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        Bundle args = getArguments();
        if (args != null)
        {
            // Set article based on argument passed in
            updateArticleView(args.getInt(ARG_POSITION));
        }
        else if (mCurrentPosition != -1)
        {
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position)
    {
        TextView article = (TextView) getActivity().findViewById(R.id.news);
        article.setText(Ipsum.Articles[0]);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
