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
import com.example.hotelmangementproject.interfaces.IClickMenuListener;
import com.example.hotelmangementproject.models.Menu;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{
    Context context;
    List<Menu> listMenu;
    IClickMenuListener iClickMenuListener;

    public MenuAdapter(Context context, List<Menu> listMenu, IClickMenuListener iClickMenuListener){
        this.context = context;
        this.listMenu = listMenu;
        this.iClickMenuListener = iClickMenuListener;
    }
    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_sm_item_menu,parent,false);
        return new MenuAdapter.MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = listMenu.get(position);
        if(menu == null) return;
        holder.txtId.setText(String.valueOf(position+1));
        holder.txtName.setText(menu.getName());
        holder.txtPrice.setText(String.valueOf(menu.getPrice()));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickMenuListener.onClick(menu);
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iClickMenuListener.onLongClick(menu);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listMenu != null) return listMenu.size();
        return 0;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{
        TextView txtId;
        TextView txtName;
        TextView txtPrice;
        LinearLayout linearLayout;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtID_frag_sm_item_menu);
            txtName = itemView.findViewById(R.id.txtName_frag_sm_item_menu);
            txtPrice = itemView.findViewById(R.id.txtPrice_frag_sm_item_menu);
            linearLayout = itemView.findViewById(R.id.linearlayout_frag_sm_item_menu);
        }
    }
}
