package com.example.hotelmangementproject.adapters.roomservicesAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.interfaces.IClickItemListMenuListener;
import com.example.hotelmangementproject.models.MenuBill;

import java.util.List;

public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuAdapter.ListMenuViewHolder> {

    private Context mContext;
    private List<MenuBill> listMenu;

    private IClickItemListMenuListener iClickItemListMenuListener;

    public ListMenuAdapter(Context mContext,List<MenuBill> listMenu,IClickItemListMenuListener iClickItemListMenuListener){
        this.mContext = mContext;
        this.listMenu = listMenu;
        this.iClickItemListMenuListener = iClickItemListMenuListener;
    }
    public void setData(List<MenuBill> listMenu){
        this.listMenu = listMenu;
    }

    @NonNull
    @Override
    public ListMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_rs_item_menu,parent,false);
        return new ListMenuAdapter.ListMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMenuViewHolder holder, int position) {
        MenuBill menuBill = listMenu.get(position);
        if(menuBill == null) {
            return;
        }
        holder.txtName.setText(menuBill.getName());
        holder.txtCount.setText(Integer.toString(menuBill.getCount()));
        holder.txtPrice.setText(Double.toString(menuBill.getPrice()));

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = holder.txtCount.getText().toString();
                int count = Integer.parseInt(value) + 1;
                holder.txtCount.setText(Integer.toString(Math.max(0,count)));
                iClickItemListMenuListener.onClickAdd(menuBill);
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = holder.txtCount.getText().toString();
                int count = Integer.parseInt(value) - 1;
                holder.txtCount.setText(Integer.toString(Math.max(0,count)));
                iClickItemListMenuListener.onClickMinus(menuBill);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listMenu !=null){
            return listMenu.size();
        }
        return 0;
    }

    public class ListMenuViewHolder extends RecyclerView.ViewHolder{
        TextView txtCount;
        TextView txtName;
        TextView txtPrice;
        Button btnAdd;
        Button btnMinus;

        public ListMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCount = itemView.findViewById(R.id.txt_count_menu);
            txtName = itemView.findViewById(R.id.txt_menu_name);
            txtPrice = itemView.findViewById(R.id.txt_menu_price);
            btnAdd = itemView.findViewById(R.id.btn_add_menu);
            btnMinus = itemView.findViewById(R.id.btn_minus_menu);
        }
    }
}
