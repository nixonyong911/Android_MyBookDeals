package nixonyong911.mybookdeals.Categories;

/**
 * Created by nixonyong911 on 14/05/2015.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nixonyong911.mybookdeals.R;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Categories extends Fragment {
    private static String[] titles;
    private RecyclerView categories;
    private CategoriesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStat) {
        titles = getResources().getStringArray(R.array.categories_data);
        View layout = inflater.inflate(R.layout.categories, container, false);
        categories = (RecyclerView) layout.findViewById(R.id.categories);
        adapter= new CategoriesAdapter(getActivity(),getData());
        categories.setAdapter(adapter);
        categories.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<categories_data> getData() {
        List<categories_data> data = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            categories_data current = new categories_data();
            current.titles = titles[i];
            data.add(current);
        }
        return data;
    }

}