package nixonyong911.mybookdeals.Categories;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import nixonyong911.mybookdeals.R;

/**
 * Created by nixonyong911 on 07/06/2015.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    List<categories_data> data = Collections.emptyList();

    public CategoriesAdapter(Context context, List<categories_data> data) {
        inflater = LayoutInflater.from(context);
        context.getResources().getStringArray(R.array.categories_data);
        this.context = context;
        this.data = data;
    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.categories_data, parent, false);
        CategoriesViewHolder viewHolder = new CategoriesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {
        categories_data current = data.get(position);
        holder.data.setText(current.titles);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView data;

        public CategoriesViewHolder(View view) {
            super(view);
            data = (TextView) view.findViewById(R.id.categoriesData);
            data.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,bookCategories.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("title", data.getText());
            context.startActivity(intent);
        }
    }
}