package com.example.ad37_nguyenmanhhung_buoi6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;


public class CreateNewNote extends AppCompatActivity {

    ArrayList<String> noteType;
    Spinner spNoteType;
    TextView tvTime, tvDate, tvTags, tvWeeks;
    TimePicker timePicker;
    DatePicker datePicker;
    Button btnTimeOK, btnTimeCancel, btnDateOK, btnDateCancel, btnTune;
    int hour, minute, day, month, year;
    ArrayList<Integer> tagsItem = new ArrayList<>();
    ArrayList<Integer> weeksItem = new ArrayList<>();
    int selectedTune = 0;
    int position = 0;
    boolean[] checkOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_note);
        setTitle("Create New Note");
        initView();
        createTypeSpinner();
        getCurrentTime();

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();

            }
        });

        tvTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] tagList = {"Family", "Game", "Android", "VTC", "Friend"};
                final boolean[] checkList = {false, false, false, false, false};
                AlertDialog alertDialog = new AlertDialog.Builder(CreateNewNote.this)
                        .setTitle("Choose tags")
                        .setCancelable(false)
                        .setMultiChoiceItems(tagList, checkList, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    if (!tagsItem.contains(which)) {
                                        tagsItem.add(which);
                                    }
                                } else if (tagsItem.contains(which)) {
                                    tagsItem.remove(Integer.valueOf(which));
                                }
                                Collections.sort(tagsItem, new Comparator<Integer>() {
                                    @Override
                                    public int compare(Integer item1, Integer item2) {
                                        return item1.compareTo(item2);
                                    }
                                });
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String tags = "";
                                for (int i = 0; i < tagsItem.size(); i++) {
                                    tags = tags + tagList[tagsItem.get(i)];
                                    if (i != (tagsItem.size() - 1)) {
                                        tags = tags + ", ";
                                    }
                                }
                                if (tags.isEmpty()) {
                                    tvTags.setText("Tap to choose");
                                } else {
                                    tvTags.setText(tags);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        tvWeeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] weekList = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
                final boolean[] checkList = {false, false, false, false, false, false, false};
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateNewNote.this);
                alertDialog.setTitle("Choose tags")
                        .setCancelable(false)
                        .setMultiChoiceItems(weekList, checkList, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    if (!weeksItem.contains(which)) {
                                        weeksItem.add(which);
                                    }
                                } else if (weeksItem.contains(which)) {
                                    weeksItem.remove(Integer.valueOf(which));
                                }
                                Collections.sort(weeksItem, new Comparator<Integer>() {
                                    @Override
                                    public int compare(Integer item1, Integer item2) {
                                        return item1.compareTo(item2);
                                    }
                                });
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String weeks = "";
                                for (int i = 0; i < weeksItem.size(); i++) {
                                    weeks = weeks + weekList[weeksItem.get(i)];
                                    if (i != (weeksItem.size() - 1)) {
                                        weeks = weeks + ", ";
                                    }
                                }
                                if (weeks.isEmpty()) {
                                    tvWeeks.setText("Tap to choose");
                                } else {
                                    tvWeeks.setText(weeks);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        btnTune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getBaseContext(), v);
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.tune_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mnFromFile:
                                Toast.makeText(CreateNewNote.this, "From File", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.mnFromDefaults:
                                showDefaultTune();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnSave:
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnCancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initView() {
        tvTime = findViewById(R.id.tvTime);
        tvDate = findViewById(R.id.tvDate);
        spNoteType = findViewById(R.id.spNoteType);
        tvTags = findViewById(R.id.tvTags);
        tvWeeks = findViewById(R.id.tvWeeks);
        btnTune = findViewById(R.id.btnTune);
    }

    public void getCurrentTime() {
        String sHour, sMinute, sDay, sMonth;
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        if (hour > 9) {
            sHour = String.valueOf(hour);
        } else {
            sHour = "0" + hour;
        }
        if (minute > 9) {
            sMinute = String.valueOf(minute);
        } else {
            sMinute = "0" + minute;
        }
        if (day > 9) {
            sDay = String.valueOf(day);
        } else {
            sDay = "0" + day;
        }
        if (month > 9) {
            sMonth = String.valueOf(month);
        } else {
            sMonth = "0" + (month);
        }
        int year = cal.get(Calendar.YEAR);
        tvTime.setText(sHour + ":" + sMinute);
        tvDate.setText(sDay + "/" + sMonth + "/" + year);
    }

    public void createTypeSpinner() {
        noteType = new ArrayList<>();
        noteType.add("Work");
        noteType.add("Friend");
        noteType.add("Family");
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, noteType);
        spNoteType.setAdapter(adapter);
    }

    public void showTimeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.time_picker);
        dialog.setCanceledOnTouchOutside(false);
        timePicker = dialog.findViewById(R.id.time_picker);
        btnTimeOK = dialog.findViewById(R.id.btnTimeOK);
        btnTimeCancel = dialog.findViewById(R.id.btnTimeCancel);
        btnTimeOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sHour, sMinute;
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();
                if (hour > 9) {
                    sHour = String.valueOf(hour);
                } else {
                    sHour = "0" + hour;
                }
                if (minute > 9) {
                    sMinute = String.valueOf(minute);
                } else {
                    sMinute = "0" + minute;
                }
                tvTime.setText(sHour + ":" + sMinute);
                dialog.dismiss();
            }
        });
        btnTimeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showDateDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.date_picker);
        dialog.setCanceledOnTouchOutside(false);
        btnDateOK = dialog.findViewById(R.id.btnDateOK);
        btnDateCancel = dialog.findViewById(R.id.btnDateCancel);
        datePicker = dialog.findViewById(R.id.date_picker);
        btnDateOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDay, sMonth;
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth() + 1;
                year = datePicker.getYear();
                if (day > 9) {
                    sDay = String.valueOf(day);
                } else {
                    sDay = "0" + day;
                }
                if (month > 9) {
                    sMonth = String.valueOf(month);
                } else {
                    sMonth = "0" + (month);
                }
                tvDate.setText(sDay + "/" + sMonth + "/" + year);
                dialog.dismiss();
            }
        });

        btnDateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showDefaultTune() {

        final String[] tunes = {"Nexus Tune", "WindowPhone Tune",
                "Pep Tune", "Nokia Tune", "Other"};
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateNewNote.this);
        alertDialog.setTitle("Set tune:")
                .setCancelable(false)
                .setSingleChoiceItems(tunes, selectedTune, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedTune = which;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        position = selectedTune;
                        dialog.dismiss();
                        alertDialog.setSingleChoiceItems(tunes, position, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }
}
