package com.example.hotelmangementproject.adapters.systemmanagementAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.interfaces.IClickPriceRule;
import com.example.hotelmangementproject.models.CalMoney;

import java.util.List;

public class PriceRuleAdapter extends RecyclerView.Adapter<PriceRuleAdapter.PriceRuleViewHolder> {
    private Context mContext;
    private List<CalMoney> listPriceRule;
    private IClickPriceRule iClickPriceRule;

    public PriceRuleAdapter(Context mContext, List<CalMoney> listPriceRule, IClickPriceRule iClickPriceRule){
        this.mContext = mContext;
        this.listPriceRule = listPriceRule;
        this.iClickPriceRule = iClickPriceRule;
    }

    @NonNull
    @Override
    public PriceRuleAdapter.PriceRuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_sm_item_price_rule,parent,false);
        return new PriceRuleAdapter.PriceRuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceRuleAdapter.PriceRuleViewHolder holder, int position) {
        CalMoney calMoney = listPriceRule.get(position);

        if(calMoney == null) return;

        holder.txtId.setText(Integer.toString(position + 1));
        holder.txtType.setText(calMoney.getType());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickPriceRule.onClick(calMoney);
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iClickPriceRule.onLongClick(v,calMoney);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listPriceRule != null) return listPriceRule.size();
        return 0;
    }

    public class PriceRuleViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        TextView txtId;
        TextView txtType;

        public PriceRuleViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearlayout_frag_sm_item_price_rule);
            txtId = itemView.findViewById(R.id.txtID_frag_sm_item_price_rule);
            txtType = itemView.findViewById(R.id.txtType_frag_sm_item_price_rule);
        }
    }
}
