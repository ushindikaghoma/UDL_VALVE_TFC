package com.example.valveonline.Promotion.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valveonline.Faculte.data.FaculteAdapter;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.R;

import java.util.ArrayList;
import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionListAdapter>{

    Context context;
    private List<PromotionResponse> list;

    public PromotionAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public PromotionListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_promotion, parent, false);
        return new PromotionListAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionListAdapter holder, int position) {
        PromotionResponse promotionResponse = list.get(position);
        holder.textView_code_promotion.setText("" + promotionResponse.getCodePromotion());
        holder.textView_designation_promotion.setText("" + promotionResponse.getDesignationPromotion());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class PromotionListAdapter extends RecyclerView.ViewHolder
    {
        TextView textView_code_promotion;
        TextView textView_designation_promotion;

        LinearLayout linearLayoutArticle;

        public PromotionListAdapter(@NonNull View itemView) {
            super(itemView);

            textView_code_promotion = itemView.findViewById(R.id.modelPromotionCode);
            textView_designation_promotion = itemView.findViewById(R.id.modelPromotionDesignation);
            linearLayoutArticle = itemView.findViewById(R.id.model_article_linear_selectionner);

        }
    }

    public void setList(List<PromotionResponse> list)
    {
        this.list.clear();
        this.list.addAll(list);
    }
}
