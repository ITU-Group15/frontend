package com.itugroup15.channelx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
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
import android.widget.TextView;
import android.widget.Toast;

import com.itugroup15.channelxAPI.APIClient;
import com.itugroup15.channelxAPI.APIController;
import com.itugroup15.channelxAPI.model.MessageRequest;
import com.itugroup15.channelxAPI.model.MessageResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.itugroup15.channelx.LoginActivity.PREFS_NAME;

public class ChatActivity extends AppCompatActivity {

    private static final int MESSAGE_FETCH_INTERVAL_SEC = 1;

    private final Handler handler = new Handler();

    private int user_id; // unused, TODO use to check whether creator
    private int channelID;
    private int userID;
    private int ownerID;
    private String username;
    private String token;

    private APIController apiController;
    private SharedPreferences settings;
    private ChannelAdapter adapter;
    private RecyclerView rv;
    private View sendButton;
    private TextInputEditText chatTextInput;

    public static Intent getIntent(Context context, int channelID, int userID, int channeOwnerID){
        Intent intent =new Intent(context, ChatActivity.class);
        intent.putExtra(context.getString(R.string.intent_channel_id),channelID);
        intent.putExtra(context.getString(R.string.intent_user_id),userID);
        intent.putExtra(context.getString(R.string.channel_owner_id),channeOwnerID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        username = settings.getString(getString(R.string.sharedpref_email), "");
        channelID = getIntent().getExtras().getInt(getString(R.string.intent_channel_id));
        user_id = getIntent().getExtras().getInt(getString(R.string.intent_user_id));
        ownerID = getIntent().getExtras().getInt(getString(R.string.channel_owner_id));

        apiController = APIClient.getClient().create(APIController.class);

        sendButton = (View) findViewById(R.id.sendMessage);
        sendButton.setBackgroundResource(R.drawable.ic_send);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ChannelAdapter(null, this);
        rv  = findViewById(R.id.chatRV);
        rv.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        if(adapter.messages != null)
            rv.scrollToPosition(adapter.messages.size() - 1);

        chatTextInput = findViewById(R.id.chatTextInputET);
        chatTextInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && adapter.messages != null) {
                    rv.scrollToPosition(adapter.messages.size()-1);
                }
            }
        });
        apiController = APIClient.getClient().create(APIController.class);

        fetchMessages();
    }

    /* Show options on toolbar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_chat_action_menu, menu);
        if(user_id != ownerID) { // if not owner
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
        String inputText = chatTextInput.getText().toString();
        if(inputText.equals(""))
            return;
        Call<MessageResponse> call = apiController
                .sendMessage(settings.getString("authToken", "")
                        , new MessageRequest(channelID, inputText));

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response){
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(adapter.getContext(), getString(R.string.toast_connection_error),
                        Toast.LENGTH_SHORT).show();
            }
        });
        chatTextInput.setText("");
    }

    public void fetchMessages(){
        final int flag =0;
        final Runnable runnableCR = new Runnable() {
            @Override
            public void run() {
                Call<MessageResponse> call = apiController
                        .getMessages(settings.getString("authToken", "")
                                , new MessageRequest(channelID));
                call.enqueue(new Callback<MessageResponse>() {

                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        try {
                            if (adapter.addItemsToEnd(response.body().getContext())) {
                                rv.scrollToPosition(adapter.messages.size() - 1);
                            }
                        }
                        catch (Exception e){
                            handler.removeCallbacks(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(adapter.getContext(), getString(R.string.toast_connection_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                handler.postDelayed(this, MESSAGE_FETCH_INTERVAL_SEC*1000);
            }
        };
        handler.postDelayed(runnableCR, MESSAGE_FETCH_INTERVAL_SEC*1000);
    }

    private class ChannelAdapter extends RecyclerView.Adapter<ChatActivity.ChannelAdapter.ViewHolder> {

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

        private List<MessageResponse.Context> messages = new ArrayList<>();
        private Context context;

        public ChannelAdapter(List<MessageResponse.Context> messages, Context context) {
            if(messages!= null) // always null
                this.messages.addAll(messages);
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
            holder.senderEmail.setText(Integer.toString(messages.get(position).getUserID())); // User id to user nickname ??
            holder.message.setText(messages.get(position).getMessage());
            String humenReadableTime = "";
            String time = messages.get(position).getCreatedAt();
            int flag = 0;
            for(int i = 0; i < time.length(); i++){
                if(flag > 0){
                    if(flag == 2 && time.charAt(i) == ':') break;
                    if(time.charAt(i) == ':') flag++;
                    humenReadableTime += time.charAt(i);
                }
                if(time.charAt(i) == 'T') flag = 1;
            }
            holder.time.setText(humenReadableTime); // TODO to human readable time
        }

        @Override
        public int getItemCount() {
            if (messages != null)
                return messages.size();
            else
                return 0;
        }

        public void addItem(MessageResponse.Context message){
            messages.add(message);
            notifyItemInserted(messages.size() - 1);
        }

        public boolean addItemsToEnd(List<MessageResponse.Context> fetchedMessages){
            Boolean flag = false;
            int prevItemCount = getItemCount();
            for(MessageResponse.Context message:fetchedMessages.subList(prevItemCount, fetchedMessages.size())){
                addItem(message);
                flag = true;
            }
            return flag;
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

        public List<MessageResponse.Context> getMessages(){
            return messages;
        }

        private Context getContext() {
            adapter.notifyDataSetChanged();
            return context;
        }
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null); // removes all callbacks
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
        super.onBackPressed();
    }
}
