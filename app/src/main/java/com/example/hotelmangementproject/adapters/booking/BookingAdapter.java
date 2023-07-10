package com.example.hotelmangementproject.adapters.booking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.HistoryAdapter;
import com.example.hotelmangementproject.interfaces.IClickBookingListener;
import com.example.hotelmangementproject.models.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    List<Booking> bookingList;
    Context mContext;
    IClickBookingListener iClickBookingListener;

    public BookingAdapter(Context context, List<Booking> bookingList, IClickBookingListener iClickBookingListener){
        this.mContext = context;
        this.bookingList = bookingList;
        this.iClickBookingListener = iClickBookingListener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_bk_item_main,parent,false);
        return new BookingAdapter.BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        if(booking == null) return;
        holder.txtName.setText(booking.getCustomerName());
        holder.txtPhoneNumber.setText(booking.getPhoneNumber());
        holder.txtDate.setText(booking.getCheckin()+ " - " + booking.getCheckout());

        if(booking.getState() == Booking.IS_NOT_CHECKED_IN_YET){
            holder.txtState.setTextColor(Color.parseColor("#33c442"));
        }
        else if(booking.getState() == Booking.CHECKED_IN){
            holder.txtState.setTextColor(Color.parseColor("#b5110b"));

        }
        else{
            holder.txtState.setTextColor(Color.parseColor("#bfa108"));
        }
        holder.txtState.setText(booking.getStringState());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickBookingListener.onClick(booking);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(bookingList != null) {
            return bookingList.size();
        }
        return 0;
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate;
        TextView txtName;
        TextView txtPhoneNumber;
        TextView txtState;
        RelativeLayout relativeLayout;
        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txt_date_frag_bk_item_main);
            txtName = itemView.findViewById(R.id.txt_name_frag_bk_item_main);
            txtPhoneNumber = itemView.findViewById(R.id.txt_phonenumber_frag_bk_item_main);
            txtState = itemView.findViewById(R.id.txt_state_frag_bk_item_main);
            relativeLayout = itemView.findViewById(R.id.relativelayout_cover_frag_bk_item_main);
        }
    }
}
