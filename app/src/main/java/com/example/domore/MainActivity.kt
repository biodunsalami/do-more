package com.example.domore


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.domore.databinding.ActivityMainBinding
import com.example.domore.ui.fragments.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle

    var currentLabel = Label.GENERAL

    var calender = Calendar.getInstance()
    private var dueDate = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        drawerLayout = binding.drawerLayout

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

        navController = navHostFragment.navController


        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarToggle.syncState()

        //handles navDrawer items navigation
        NavigationUI.setupWithNavController(binding.navView, navController)

        binding.arrow.setOnClickListener {
            Toast.makeText(this, "Works", Toast.LENGTH_SHORT).show()
            TODO("Make this work")
        }

        setSheetToPeek()


        setUpDueDate()
        setUpReminder()
        setUpDropDownEntryFields()

    }



    fun changeActivityFragment(direction: NavDirections) {
        navController.navigate(direction)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(actionBarToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpDueDate(){
        binding.dueDateEditText.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                { _, i, i2, i3 ->
                    calender.set(Calendar.YEAR, i)
                    calender.set(Calendar.MONTH, i2)
                    calender.set(Calendar.DAY_OF_MONTH, i3)

                    dueDate =
                        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(calender.time)
                    binding.dueDateEditText.setText(dueDate)
                },
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            )
            binding.dueDateTextInput.hint = ""
            datePicker.show()
        }
    }

    private fun setUpReminder(){

        binding.taskReminderTextView.setOnClickListener {
            val timePicker = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                    calender.set(Calendar.HOUR, i)
                    calender.set(Calendar.MINUTE, i2)


                    var hour = i
                    var am_pm = ""

                    // AM_PM decider logic
                    when {hour == 0 -> { hour += 12
                        am_pm = "AM"
                    }
                        hour == 12 -> am_pm = "PM"
                        hour > 12 -> { hour -= 12
                            am_pm = "PM"
                        }
                        else -> am_pm = "AM"
                    }

                    binding.taskReminderTextView.setText(
                        getString(R.string.reminder_time_text, hour, i2, am_pm))

                    Toast.makeText(this, "$hour:$i2$am_pm", Toast.LENGTH_SHORT).show()

                },
                calender.get(Calendar.HOUR),
                calender.get(Calendar.MINUTE),
                false
            )
            binding.taskReminderTextInput.hint = ""
            timePicker.show()
        }
    }

    private fun setSheetToPeek(){
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 500
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun hideSheet(){
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            isHideable = true
            this.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }


    private fun setUpDropDownEntryFields(){
        val labels = resources.getStringArray(R.array.labels)
        val labelArrayAdapter = ArrayAdapter(this, R.layout.item_drop_down, labels)
        binding.labelAutocompleteTextview.setAdapter(labelArrayAdapter)

        val priorities = resources.getStringArray(R.array.priority)
        val nationalityArrayAdapter = ArrayAdapter(this, R.layout.item_drop_down, priorities)
        binding.priorityAutocompleteTextview.setAdapter(nationalityArrayAdapter)


        //Override OnResume and call this function in there to make the drop down items persist
    }

    //Hide the bottom sheet or drawer when back is pressed
    override fun onBackPressed() {
        if (binding.bottomSheet.isVisible || this.drawerLayout.isDrawerOpen(GravityCompat.START) ) {
            setSheetToPeek()
            binding.bottomSheet.visibility = View.INVISIBLE
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    enum class Label(val labelName: String){
        GENERAL("General"),
        PERSONAL("Personal"),
        WORK("Work"),
        SCHOOL("School"),
        HOME("Home"),
        OTHERS("Others")
    }

}