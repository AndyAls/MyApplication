package cn.evun.fragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvClient;
    private TextView tvContact;
    private View client;
    private View contact;
    private ClientFragment clientFragment;
    private ContactFragment contactFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        tvClient = (TextView) findViewById(R.id.tv_client);
        tvContact = (TextView) findViewById(R.id.tv_contact);
        client = findViewById(R.id.client);
        contact = findViewById(R.id.contact);
        tvClient.setSelected(true);
        client.setBackgroundColor(getColor(R.color.green));
        initFragment();
    }

    private void initFragment() {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        clientFragment = new ClientFragment();
        transaction.add(R.id.fl, clientFragment).commit();
    }

    public void onClick(View view) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (view.getId()){
            case R.id.ll_client:
                tvClient.setSelected(true);
                client.setBackgroundColor(getColor(R.color.green));
                tvContact.setSelected(false);
                contact.setBackgroundColor(getColor(R.color.white));

                if (clientFragment==null){
                    clientFragment=new ClientFragment();
                }
                if (!clientFragment.isAdded()){
                    transaction.add(R.id.fl,clientFragment);
                }else {
                    if (contactFragment!=null){
                        transaction.hide(contactFragment);
                        transaction.show(clientFragment);
                    }
                }
                transaction.commit();

                break;
            case R.id.ll_contact:
                tvClient.setSelected(false);
                client.setBackgroundColor(getColor(R.color.white));
                tvContact.setSelected(true);
                contact.setBackgroundColor(getColor(R.color.green));

                if (contactFragment==null){
                    contactFragment=new ContactFragment();
                }
                if (!contactFragment.isAdded()){
                    transaction.add(R.id.fl,contactFragment);
                }
                    if (clientFragment!=null){
                        transaction.hide(clientFragment);
                        transaction.show(contactFragment);
                    }

                transaction.commit();
                break;
        }
    }
}
