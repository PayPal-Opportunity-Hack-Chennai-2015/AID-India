package com.eurekakids.euraka1;

/**
 * Created by Kirubanand on 12/09/2015.
 */
import android.app.Fragment;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.eurekakids.com.eurekakids.db.DatabaseHandler;
import com.eurekakids.db.datamodel.Assessment;
import com.eurekakids.db.datamodel.Student;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class tam_page extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    MainActivity main;
    CharSequence Titles[]={"Tamil","English","Maths"};
    int Numboftabs =3;

	tam_fragment tamil_frament = null;
	eng_fragment english_fragment = null;
	math_fragment maths_fragment = null;

	Student student;
	int centre_id;
	List<WeakReference<Fragment>> fragList = new ArrayList<WeakReference<Fragment>>();


	@Override
	public void onAttachFragment (Fragment fragment) {
		fragList.add(new WeakReference(fragment));
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_child);
        Intent list_item = getIntent();
        student = (Student) list_item.getSerializableExtra("list_item");
		centre_id = list_item.getExtras().getInt("CENTRE_ID");

       // TextView username = (TextView) findViewById(R.id.username);
       // username.setText(name);
          Toast.makeText(getApplicationContext(),"Quick select Child from Navigation drawer",Toast.LENGTH_LONG).show();
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        setTitle(student.getStudentName());


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		ArrayList<Student> students = db.getAllStudentsByCentreId(centre_id);
		db.close();


		Student[] names = new Student[students.size()];
		for(int i =0; i< students.size(); i++){
			names[i] = students.get(i);
		}
        ListAdapter nadapter = new ArrayAdapter<Student>(this,android.R.layout.simple_expandable_list_item_1,names);
        ListView nview = (ListView) findViewById(R.id.name_list);
        nview.setAdapter(nadapter);
        nview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = parent.getItemAtPosition(position).toString();
                setTitle(itemSelected);
                drawerLayout.closeDrawers();
            }
        });
                // Setting the ViewPager For the SlidingTabsLayout
                tabs.setViewPager(pager);

                //Initializing NavigationView
                navigationView = (NavigationView) findViewById(R.id.navigation_view);

                //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {


                        //Checking if the item is in checked state or not, if not make it in checked state
                        if (menuItem.isChecked()) menuItem.setChecked(false);
                        else menuItem.setChecked(true);

                        //Closing drawer on item click
                        drawerLayout.closeDrawers();

                        //Check to see which item was being clicked and perform appropriate action
                        switch (menuItem.getItemId()) {


                            //Replacing the main content with ContentFragment Which is our Inbox View;
                            case R.id.tamil:
                                Toast.makeText(getApplicationContext(), "Tamil Selected", Toast.LENGTH_SHORT).show();
                                tamil_frament = new tam_fragment();

								int skill = 1;
								ViewGroup viewGroup = (ViewGroup) v;
								ViewGroup linear = (ViewGroup) viewGroup.getChildAt(0);
								int view_count = linear.getChildCount();

								for (int i = 0; i < view_count; i++) {
									View view = linear.getChildAt(i);
									getAssessmentValue()
									((Switch) view).isChecked();
									//new Assessment(student.getStudentId(), skill, (check_value == true) ?1 : 0);
								}
                                setTitle(R.string.nav_sub_1);
//								getFragmentManager().get
                                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frame, tamil_frament);
                                fragmentTransaction.commit();
                                return true;

                            // For rest of the options we just show a toast on click

                            case R.id.english:
                                Toast.makeText(getApplicationContext(), "English Selected", Toast.LENGTH_SHORT).show();
                                eng_fragment fragment1 = new eng_fragment();
                                setTitle(R.string.nav_sub_2);
                                android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction1.replace(R.id.frame, fragment1);
								fragmentTransaction1.attach(fragment1);
//								fragmentTransaction1.
                                fragmentTransaction1.commit();
                                return true;
                            case R.id.math:
                                Toast.makeText(getApplicationContext(), "Maths Selected", Toast.LENGTH_SHORT).show();
                                math_fragment fragment2 = new math_fragment();
                                setTitle(R.string.nav_sub_3);
                                android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction2.replace(R.id.frame, fragment2);
                                fragmentTransaction2.commit();
                            default:
                                //Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                                return true;

                        }
                    }
                });

                // Initializing Drawer Layout and ActionBarToggle
                drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
                ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                        super.onDrawerOpened(drawerView);
                    }
                };


                //Setting the actionbarToggle to drawer layout
                drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.sub_menu, menu);
                return true;
            }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Save Changes?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                dialog.cancel();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
                Intent getListintent = new Intent(tam_page.this,Listscreen.class);
                startActivity(getListintent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                if(id == R.id.action_save){
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
//					ViewGroup viewGroup=  (ViewGroup)tamil_frament.getView();
//					int view_count = viewGroup.getChildCount();
//					for(int i=0; i<view_count; i++){
//						View view = viewGroup.getChildAt(i);
//					}
					int skill = 1;
					List<android.support.v4.app.Fragment> allFragments = getSupportFragmentManager().getFragments();
					for(android.support.v4.app.Fragment fragment : allFragments){

						ViewGroup viewGroup = (ViewGroup) fragment.getView();
						ViewGroup linear = (ViewGroup) viewGroup.getChildAt(0);
						int view_count = linear.getChildCount();

						for (int i = 0; i < view_count; i++) {
							View view = linear.getChildAt(i);
							boolean check_value = ((Switch) view).isChecked();
							new Assessment(student.getStudentId(), skill, (check_value == true) ?1 : 0);
						}
					}
                    return true;
                }

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_spinner) {
                    Intent getSpinnerIntent = new Intent(this,Spinnerscreen.class);
                    startActivity(getSpinnerIntent);
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }

}