package com.example.android.lab01_2;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HeadlineFragment extends Fragment
{
    static final String ARG_POSITION = "position";

    int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_headline, container, false);

        // Display Headlines listView
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        ArrayAdapter<String> listAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Ipsum.Headlines);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                TextView article = (TextView) getActivity().findViewById(R.id.news);
                article.setText(Ipsum.Articles[position]);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
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