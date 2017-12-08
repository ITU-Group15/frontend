package com.itugroup15.channelx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itugroup15.channelxAPI.model.Message;

import java.util.ArrayList;
import java.util.List;

import static com.itugroup15.channelx.LoginActivity.PREFS_NAME;

public class ChatActivity extends AppCompatActivity {

    private int user_id;
    private String username;
    private String token;

    private SharedPreferences settings;
    private ChannelAdapter adapter;
    private RecyclerView rv;
    private View sendButton;

    public static Intent getIntent(Context context ){
        Intent intent =new Intent(context, ChatActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        username = settings.getString(getString(R.string.sharedpref_email), "");
        sendButton = (View) findViewById(R.id.sendMessage);
        sendButton.setBackgroundResource(R.drawable.ic_send);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Debug
        ArrayList<Message> debug = new ArrayList<>();
        for(int i = 0 ; i < 50; i++){
            debug.add(new Message("Message text: " + Integer.toString(i), "Time", "email"));
        }

        adapter = new ChannelAdapter(debug, this);
        rv  = findViewById(R.id.chatRV);
        rv.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.scrollToPosition(adapter.messages.size() - 1);
        EditText textView = findViewById(R.id.chatTextInputET);
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rv.scrollToPosition(adapter.messages.size()-1);
                }
            }
        });




    }

    /* Show options on toolbar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_chat_action_menu, menu);
        if(false) { // if not owner
            menu.removeItem(R.id.action_edit);
        }
        return true;
    }

    /* Handle the selections in options menu */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                // TODO
                return true;
            case R.id.action_info:
                // TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void sendMessage (View view){
        Toast.makeText(this, "Pressed send button",
                Toast.LENGTH_LONG).show();
    }




    public class ChannelAdapter extends RecyclerView.Adapter<ChatActivity.ChannelAdapter.ViewHolder> {

    public  class ViewHolder extends RecyclerView.ViewHolder {
            TextView senderEmail;
            TextView message;
            TextView time;
            public ViewHolder(View itemView) {
                super(itemView);
                senderEmail = itemView.findViewById(R.id.chat_list_layout_senderemail);
                message= itemView.findViewById(R.id.chat_list_layout_message);
                time = itemView.findViewById(R.id.chat_list_layout_time);
            }
        }

        private List<Message> messages;
        private Context context;

        public ChannelAdapter(List<Message> messages, Context context) {
            this.messages = messages;
            this.context = context;
        }

        @Override
        public ChatActivity.ChannelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View contactView = inflater.inflate(R.layout.chat_layout, parent, false);

            return new ChatActivity.ChannelAdapter.ViewHolder(contactView);
        }

        @Override
        public void onBindViewHolder(ChatActivity.ChannelAdapter.ViewHolder holder, int position) {

            Message message = messages.get(position);
            if (message.getSenderEmail() != null)
                holder.senderEmail.setText(message.getSenderEmail());
            else
                holder.senderEmail.setText("Err");

            if (message.getMessage() != null)
                holder.message.setText(message.getMessage());
            else
                holder.message.setText("Err");

            if (message.getTime() != null)
                holder.time.setText(message.getTime());
            else
                holder.time.setText("Err");
        }

        @Override
        public int getItemCount() {
            if (messages != null)
                return messages.size();
            else
                return 0;
        }

        private Context getContext() {
            return context;
        }

        public void swap(List list){
            if (messages != null) {
                messages.clear();
                messages.addAll(list);
            }
            else {
                messages = list;
            }
            notifyDataSetChanged();
        }
    }


}
