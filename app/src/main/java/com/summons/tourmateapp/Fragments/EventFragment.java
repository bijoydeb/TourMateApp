package com.summons.tourmateapp.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.summons.tourmateapp.Adapter.EventListAdapter;
import com.summons.tourmateapp.Database.EventManager;
import com.summons.tourmateapp.Database.ExpenseManager;
import com.summons.tourmateapp.Database.MomentManager;
import com.summons.tourmateapp.Model.Event;
import com.summons.tourmateapp.Model.Expense;
import com.summons.tourmateapp.Model.Moment;
import com.summons.tourmateapp.R;
import com.summons.tourmateapp.SignUpActivity;
import com.summons.tourmateapp.Utils.TourMateSharedPreference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class EventFragment extends Fragment {
    
    Context context;
    TextView createEventTV;
    ListView eventListView;
    EditText travelDestinationET;
    EditText estimateBudgetET;
    EditText fromDateET;
    EditText toDateET;
    Button submitButton;
    Button cancelButton;
    EditText expenseDetailsEditText;
    EditText momentDetailsEditText;
    ImageView momentImageView;
    EditText amountEditText;
    EditText dateEditText;
    EditText timeEditText;
    TourMateSharedPreference sharedPreference;
    EventManager eventManager;
    ExpenseManager expenseManager;
    MomentManager momentManager;
    String userId;
    Event event;
    Expense expense;
    Moment moment;
    public ArrayList<Event> eventArrayList;
    EventListAdapter eventListAdapter;

    public String savedImageURL="";
    private File file;
    private static File dir = null;
    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        eventListView = (ListView) view.findViewById(R.id.eventListView);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        this.context = getActivity().getApplicationContext();

        expenseManager = new ExpenseManager(context);
        eventManager = new EventManager(context);
        momentManager = new MomentManager(context);
        eventArrayList = new ArrayList<>();


        sharedPreference = new TourMateSharedPreference(context);
        userId = sharedPreference.getUserId();

        eventArrayList = eventManager.getAllEvent(userId);

        eventListAdapter = new EventListAdapter(context, eventArrayList);

        eventListView.setAdapter(eventListAdapter);

        eventListAdapter.notifyDataSetChanged();
        registerForContextMenu(eventListView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager manager = getActivity().getFragmentManager();
                CreateEventDialog dialog = new CreateEventDialog();
                dialog.show(manager, "Event_Dialog");
            }
        });
        return view;


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }


    public void refreshPage() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;

        String eventId = eventArrayList.get(pos).getUserId();

        switch (item.getItemId()) {
            case R.id.addExpense:
                Bundle args = new Bundle();
                args.putString("eventId", eventId);
                android.app.FragmentManager manager = getActivity().getFragmentManager();
                AddExpenseDialog dialog = new AddExpenseDialog();
                dialog.setArguments(args);
                dialog.show(manager, "Expense_Dialog");
                return true;

            case R.id.addMoment:
                Bundle bundle = new Bundle();
                bundle.putString("eventId", eventId);
                android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                AddMomentDialog addMomentDialog = new AddMomentDialog();
                addMomentDialog.setArguments(bundle);
                addMomentDialog.show(fragmentManager, "Moment_Dialog");
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }


    @SuppressLint("ValidFragment")
    private class CreateEventDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.event_dialogue, null);
            dialogBuilder.setView(dialogView);

            travelDestinationET = (EditText) dialogView.findViewById(R.id.travelDestinationET);
            estimateBudgetET = (EditText) dialogView.findViewById(R.id.estimateBudgetET);
            fromDateET = (EditText) dialogView.findViewById(R.id.fromDateET);
            toDateET = (EditText) dialogView.findViewById(R.id.toDateET);
            submitButton = (Button) dialogView.findViewById(R.id.submitButton);
            cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);

            fromDateET.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                            fromDateET.setText(selectedDay + "/" + selectedMonth + "/" + selectedYear);
                        }
                    }, year, month, day);
                    datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    datePickerDialog.show();
                }
            });

            toDateET.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                            toDateET.setText(selectedDay + "/" + selectedMonth + "/" + selectedYear);

                        }
                    }, year, month, day);
                    datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    datePickerDialog.show();
                }
            });

//            dialogBuilder.setTitle("Create Event");
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String destination = travelDestinationET.getText().toString().trim();
                    String budget = estimateBudgetET.getText().toString().trim();
                    String fromDate = fromDateET.getText().toString().trim();
                    String toDate = toDateET.getText().toString().trim();


                    if (destination.equals("")) {
                        Toast.makeText(getActivity(), "Enter Travel Destination", Toast.LENGTH_SHORT).show();
                    } else if (budget.equals("")) {
                        Toast.makeText(getActivity(), "Enter Estimate Budget", Toast.LENGTH_SHORT).show();
                    } else if (fromDate.equals("")) {
                        Toast.makeText(getActivity(), "Enter From Date", Toast.LENGTH_SHORT).show();
                    } else if (toDate.equals("")) {
                        Toast.makeText(getActivity(), "Enter To Date", Toast.LENGTH_SHORT).show();
                    } else {
                        event = new Event(userId,destination, budget, fromDate, toDate);
                        long success = eventManager.addEvent(event);
                        if (success > 0) {
                            Log.e("EF","user id : "+userId);
                            refreshPage();
                            getDialog().dismiss();
                            Toast.makeText(getActivity(), "Event Create Successful", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "Event Create Failed !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

            AlertDialog alertDialog = dialogBuilder.create();

            return alertDialog;

        }

    }

    @SuppressLint("ValidFragment")
    private class AddExpenseDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.expence_dialoge, null);
            dialogBuilder.setView(dialogView);

            Bundle mArgs = getArguments();
            final String eventId = mArgs.getString("eventId");
            expenseDetailsEditText = (EditText) dialogView.findViewById(R.id.expenseDetailsEditText);
            amountEditText = (EditText) dialogView.findViewById(R.id.amountEditText);
            dateEditText = (EditText) dialogView.findViewById(R.id.dateEditText);
            timeEditText = (EditText) dialogView.findViewById(R.id.timeEditText);
            submitButton = (Button) dialogView.findViewById(R.id.submitButton);
            cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);

            dateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                            dateEditText.setText(selectedDay + "/" + selectedMonth + "/" + selectedYear);
                        }
                    }, year, month, day);
                    datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    datePickerDialog.show();
                }
            });

            timeEditText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            try {
                                timeEditText.setText(Hour12(selectedHour + ":" + selectedMinute + ":00"));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    },hour,minute,false);
                    timePickerDialog.show();
                }

            });



            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String expense_details = expenseDetailsEditText.getText().toString().trim();
                    String amount = amountEditText.getText().toString().trim();
                    String date = dateEditText.getText().toString().trim();
                    String time = timeEditText.getText().toString().trim();

                    if (expense_details.equals("")) {
                        Toast.makeText(getActivity(), "Enter Expense Details!", Toast.LENGTH_SHORT).show();
                    } else if (amount.equals("")) {
                        Toast.makeText(getActivity(), "Enter Amount!", Toast.LENGTH_SHORT).show();
                    } else if (date.equals("")) {
                        Toast.makeText(getActivity(), "Enter To Date!", Toast.LENGTH_SHORT).show();
                    } else {
                        expense = new Expense(userId, eventId, expense_details, amount, date, time);
                        long success = expenseManager.addExpense(expense);
                        if (success > 0) {
                            Toast.makeText(getActivity(), "Expense Add Successful", Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Expense Add Failed !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

            AlertDialog alertDialog = dialogBuilder.create();

            return alertDialog;

        }

    }

    @SuppressLint("ValidFragment")
    private class AddMomentDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.moment_dialoge, null);
            dialogBuilder.setView(dialogView);

            Bundle mArgs = getArguments();
            final String eventId = mArgs.getString("eventId");

            momentDetailsEditText = (EditText) dialogView.findViewById(R.id.momentDetailsEditText);
            momentImageView = (ImageView) dialogView.findViewById(R.id.addImageView);
            dateEditText = (EditText) dialogView.findViewById(R.id.dateEditText);
            submitButton = (Button) dialogView.findViewById(R.id.submitButton);
            cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);

            momentImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageResource();
                }
            });
            dateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                            dateEditText.setText(selectedDay + "/" + selectedMonth + "/" + selectedYear);
                        }
                    }, year, month, day);
                    datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    datePickerDialog.show();
                }
            });


            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String moment_details = momentDetailsEditText.getText().toString().trim();
                    String date = dateEditText.getText().toString().trim();

                    if (moment_details.equals("")) {
                        Toast.makeText(getActivity(), "Enter Expense Details!", Toast.LENGTH_SHORT).show();
                    } else if (savedImageURL.equals("")) {
                        Toast.makeText(getActivity(), "Chose a Image", Toast.LENGTH_SHORT).show();
                    } else {
                        moment = new Moment(savedImageURL, moment_details, userId, eventId, date);
                        long success = momentManager.addMoment(moment);
                        if (success > 0) {
                            Toast.makeText(getActivity(), "Moment Add Successful", Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Moment Add Failed !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

            AlertDialog alertDialog = dialogBuilder.create();

            return alertDialog;

        }

    }

    public void imageResource() {
        AlertDialog alertDialog = null;
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.image_chose_layout, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        ImageView cameraButton = (ImageView) dialogView.findViewById(R.id.cameraButton);
        ImageView galleryButton = (ImageView) dialogView.findViewById(R.id.galleryButton);

        final AlertDialog finalAlertDialog = alertDialog;
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                finalAlertDialog.dismiss();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, GALLERY_REQUEST);
                finalAlertDialog.dismiss();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            final long time = System.currentTimeMillis();
            try {
                savedImageURL = saveBitmapIntoSdcard(bitmap, time + ".png");
                momentImageView.setImageBitmap(bitmap);

            } catch (final IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try {

                final Uri selectedImageUri = data.getData();
                final String url__ = getPath(selectedImageUri);
                savedImageURL = url__;
                Log.e("EF","image path : "+savedImageURL);
                momentImageView.setImageURI(selectedImageUri);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public  String getPath(Uri uri) {
        final String[] projection = {MediaStore.MediaColumns.DATA};
        final Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        final int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private String saveBitmapIntoSdcard(Bitmap bitmap22, String filename)
            throws IOException {

        createBaseDirectory();

        try {

            new Date();
            OutputStream out = null;
            file = new File(dir, "/" + filename);

            out = new FileOutputStream(file);

            bitmap22.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();
            return file.getAbsolutePath();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createBaseDirectory() {

        final String extStorageDirectory = Environment
                .getExternalStorageDirectory().toString();

        dir = new File(extStorageDirectory + "/TourMate");

        if (EventFragment.dir.mkdir()) {
            System.out.println("Directory created");
        } else {
            System.out.println("Directory is not created or exists");
        }
    }

    private String Hour12(String data) throws java.text.ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Date date =dateFormat.parse(data);
        DateFormat dateFormat1= new SimpleDateFormat("h:mm a",Locale.ENGLISH);
        return dateFormat1.format(date);
    }
}

