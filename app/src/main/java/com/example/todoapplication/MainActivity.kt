package com.example.todoapplication

import android.annotation.SuppressLint
import android.app.FragmentManager.BackStackEntry
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapplication.domin.Room.TodoROOM
import com.example.todoapplication.domin.model.TodoModel
import com.example.todoapplication.repository.TodoRepository
import com.example.todoapplication.ui.theme.TodoApplicationTheme
import com.example.todoapplication.viewmodel.TaskViewModel
import java.time.Year
import java.util.Calendar
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var context= LocalContext.current
                    var room=TodoROOM.getInstence(context)
                    var repository=TodoRepository(room.dao())
                    var viewModel=TaskViewModel(repository = repository)



                    var navController= rememberNavController()
                    NavHost(navController = navController, startDestination = Destination.home.toString()) {
                        composable(Destination.home.toString()){
                            HomeScreen(viewModel = viewModel,navController)
                        }

                        composable(Destination.second.toString()+"/id={id}",
                            arguments = listOf(navArgument("id"){
                                type= NavType.StringType
                                nullable=true
                            })
                        ){baskstack->
                            val id= requireNotNull(baskstack.arguments)
                                .getString("id")
                            UpdateModule(id,viewModel,navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UpdateModule(taskId: String?,viewModel: TaskViewModel,navHostController: NavHostController){
    var tasktitle by remember {
        mutableStateOf("")
    }
    var todoid= taskId
    var id= todoid?.toInt()
    var context= LocalContext.current.applicationContext
    Scaffold(
        topBar = {
            Row(modifier =
            Modifier
                .background(Color.Cyan)
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(modifier = Modifier
                    .padding(5.dp)
                    .width(40.dp)
                    .height(40.dp),
                    onClick = {
                    navHostController.popBackStack()
                }) {
                    Icon(modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Update Task", fontSize =32.sp )
            }
        }
    ) {paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .background(Color.Black)
            .fillMaxSize()
            .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Update Your Task! ${id}", fontSize = 34.sp,
                color = Color.White, fontWeight = FontWeight(800)
            )
            Spacer(modifier = Modifier.height(100.dp))
            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
                value = tasktitle, onValueChange = {newtitle->
                    tasktitle=newtitle
                },
                placeholder = {
                    Text(text = "Write Your Next Task...")
                },
                shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 20))
            )
            Spacer(modifier = Modifier.height(100.dp))
            FloatingActionButton(onClick = {
                                           if (tasktitle!=""){
                                               if (id != null) {
                                                   viewModel.updateTitle(tasktitle,id)
                                                   Toast.makeText(context,"task Sucesfully",Toast.LENGTH_SHORT).show()
                                                   navHostController.popBackStack()
                                               }
                                           }else{
                                               Toast.makeText(context,"Please Enter your Task",Toast.LENGTH_SHORT).show()
                                           }
            },
                shape =MaterialTheme.shapes.large.copy(CornerSize(percent = 60)),
                modifier = Modifier
                    .padding(start = 230.dp)
                    .height(80.dp)
                    .width(80.dp)
                    ,
                contentColor = colorResource(id = R.color.black),
                containerColor = colorResource(id = R.color.orange)

            ) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = "",)
            }
        }


    }
}
@Composable
fun HomeScreen(viewModel: TaskViewModel,navHostController: NavHostController){
    Scaffold(
       topBar = {
                Row(modifier =
                Modifier
                    .background(Color.Cyan)
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Todo App", fontSize =32.sp )
                }
       }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.Black)
                .fillMaxSize()
        ) {
            Task(viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            Search(viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            Todos(viewModel,navHostController)
        }
    }
}

@Composable
fun Todos(viewModel: TaskViewModel,navHostController: NavHostController) {
    var task_id=0
    val task by viewModel.getTasks().collectAsState(initial = emptyList())
    LazyColumn {
        items(task){task->
            Item(task,viewModel,navHostController)
        }
    }
}

@Composable
fun Item(task:TodoModel,viewModel: TaskViewModel,navHostController: NavHostController) {

    var taskId=task.id
    var checked by remember {
        mutableStateOf(task.checked)
    }
    var color by remember {
        mutableStateOf(Color.DarkGray)
    }
    var context= LocalContext.current.applicationContext

    Card(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .height(90.dp)
    ) {

            Row(modifier = Modifier
                .fillMaxSize()
                .padding(start = 18.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Box {
                        RadioButton(selected = checked, onClick = {
                            if (checked==false){
                                checked=true
                            }else{
                                checked=false
                            }
                            viewModel.updateTask(TodoModel(task.id,task.Task,checked,task.Like,task.Date))
                        },
                            colors = RadioButtonDefaults.colors(Color.Green))
                }
                if (checked==true){
                    Text(text =task.Task, modifier = Modifier.width(180.dp),
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text ="14/02/2004", modifier = Modifier.width(180.dp),
                        fontSize = 15.sp, textDecoration = TextDecoration.LineThrough
                    )
                }else{
                    Column {
                        Text(text =task.Task, modifier = Modifier.width(180.dp)
                            , color = Color.Black,
                            fontWeight = FontWeight(700),
                            fontSize = 19.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text ="14/02/2004", modifier = Modifier.width(180.dp)
                            , color = Color.Black,
                            fontSize = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(5.dp))
                Box {
                    Row {
                        IconButton(onClick = {
                            navHostController.navigate(Destination.second.toString()+"/id=$taskId")
                        }) {
                            Icon(imageVector = Icons.Rounded.Edit, contentDescription = "")

                        }

                        IconButton(onClick = {
                            color = if (color==Color.Red){
                                Color.DarkGray
                            }else
                                Color.Red
                        }) {
                            Icon(imageVector = Icons.Rounded.Favorite, contentDescription = "",
                              tint = color )

                        }

                        IconButton(

                            onClick = {
                            viewModel.deleteTask(task)
                            Toast.makeText(context,"Item has deleted",Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "")
                        }
                    }

                }
            }



        }

    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(viewModel: TaskViewModel) {
    var search_todo by remember {
        mutableStateOf("")
    }
    var context2:Context= LocalContext.current

    val year:Int
    val month:Int
    val day:Int
    val calender=Calendar.getInstance()
    year=calender.get(Calendar.YEAR)
    month=calender.get(Calendar.MONTH)
    day=calender.get(Calendar.DAY_OF_MONTH)
    calender.time= Date()

    val date= remember {
        mutableStateOf("")
    }

    val datePickerDialog= android.app.DatePickerDialog(
        context2,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/$month/$year"
        }, year, month, day
    )


    var context= LocalContext.current.applicationContext
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        horizontalArrangement = Arrangement.Center) {
        TextField(value = search_todo, onValueChange = {newtitle->
            search_todo=newtitle
        },
            placeholder = {
                Text(text = "Write Your Next Task...")
            },
            shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 20))
            )
        Spacer(modifier = Modifier.width(10.dp))

       IconButton(modifier = Modifier
           .height(50.dp)
           .width(50.dp),
           onClick = {
           datePickerDialog.show()
       }) {
           Icon(imageVector = Icons.Rounded.DateRange, contentDescription = "",
               tint = Color.White,
               modifier = Modifier
                   .fillMaxSize()
                   .padding(end = 10.dp))
       }


        FloatingActionButton(onClick = {
           try {
              if (search_todo!=""){
                  var item=TodoModel(0,search_todo,false,false,date.value)
                  viewModel.insertTask(item)
                  search_todo=""
                  Toast.makeText(context,"Task Sucessfully inserted",Toast.LENGTH_LONG).show()
              }else{
                  Toast.makeText(context,"Please Enter Task Name",Toast.LENGTH_LONG).show()
              }

           }catch (e:Exception){
               Log.d("TAGY","error: ${e.toString()}")
           }
                                       },
            shape =MaterialTheme.shapes.large.copy(CornerSize(percent = 60)),
            modifier = Modifier
                .height(80.dp)
                .width(60.dp),
            contentColor = colorResource(id = R.color.black),
            containerColor = colorResource(id = R.color.orange)

            ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "",)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Task(viewModel: TaskViewModel) {
    val finishedTask by viewModel.getselected().collectAsState(initial = emptyList())
    val totTask by viewModel.getTasks().collectAsState(initial = emptyList())
    var completed=finishedTask.size
    var total=totTask.size






    Card(modifier = Modifier
        .padding(start = 20.dp, top = 20.dp, end = 10.dp, bottom = 10.dp)
        .height(200.dp)
        .width(350.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        border = BorderStroke(width = 2.dp, color = Color.White)
        ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()) {
            Column {

                Text(text = "Todo Done",
                    fontSize = 28.sp,
                    fontWeight = FontWeight(800),
                    color = Color.White
                )
                Text(text = "Keep it up",
                    fontSize = 23.sp,
                    color = Color.LightGray)
            }
            Spacer(modifier = Modifier.width(30.dp))
            Card(modifier = Modifier
                .width(150.dp)
                .height(150.dp),
                shape = RoundedCornerShape(300.dp),
                colors = CardDefaults.cardColors(colorResource(id = R.color.orange))) {
                Text(text = "$completed/$total",
                    fontSize = 50.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(1000),
                    modifier = Modifier.padding(top = 40.dp, start = 33.dp)
                    )
            }
        }
    }
}
