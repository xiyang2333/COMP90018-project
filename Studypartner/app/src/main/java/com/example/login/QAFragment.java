package com.example.login;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.SearchView;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;

        import static androidx.constraintlayout.widget.Constraints.TAG;

public class QAFragment extends Fragment {

    private SearchView mSearchView;
    Spinner choosetag;
    private String[] mStrs = {"Chemistry", "IT", "Business", "Math", "Physics", "Biology", "Mechanics", "Art"};
    int userId;
    int tagId;
    String userInput;
    public QAFragment() {

    }


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("??????????????????");
        Bundle bundle = getArguments();
        userId = bundle.getInt("userId");

        View rootView = inflater.inflate(R.layout.fragment_qa, container, false);
        choosetag = rootView.findViewById(R.id.spinner2);




        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        choosetag.setAdapter(adapter);
        choosetag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view instanceof TextView) {
                    tagId = tagint(((TextView) view).getText().toString());

                }
                Log.i(TAG, "viewa: " + view + ", parent: " + parent + ", position: " + position + ", id: " + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "on nothing selected");
            }
        });







        rootView.findViewById(R.id.Q1).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                if (getFragmentManager() != null) {
                    createQuestionFragment nextFra = new createQuestionFragment();
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, nextFra.newInstance(userId)).commit();
                }
            }
        });

        mSearchView = (SearchView) rootView.findViewById(R.id.searchView);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                answerQuestionFragment ansQA = new answerQuestionFragment();
                userInput = query;
                System.out.println(userInput+"@@@@@@@@@@@@@@@@@@@");
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, ansQA.newInstance(userId,tagId,userInput)).commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                return false;
            }
        });


        return rootView;
    }

    public static int tagint(String finaltag) {
        if (finaltag.equals("Chemistry")) {
            return 1;
        }
        else if (finaltag.equals("Information Technology")) {
            return 2;
        }
        else if (finaltag.equals("Business")) {
            return 3;
        }
        else if (finaltag.equals("Math")) {
            return 4;
        }else if (finaltag.equals("Physics")) {
            return 5;
        }else if (finaltag.equals("Biology")) {
            return 6;
        }else if (finaltag.equals("Mechanics")) {
            return 7;
        }else if (finaltag.equals("Art")) {
            return 8;
        }
        else return 9;

    }
}
