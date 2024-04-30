package com.example.practice1

import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.example.practice1.ui.theme.Practice1Theme
import java.io.File

object GlobalVariables {
//    var myFolderList1: MutableList<MutableList<String>> = mutableListOf()

    var folderLists: MutableList<File> = mutableListOf();
    var folderNames: MutableList<String> = mutableListOf();
    var fileLists_above: MutableList<MutableList<File>> = mutableListOf();
    var fileNames_above: MutableList<MutableList<String>> = mutableListOf();
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practice1Theme {
//                val sublist1 = mutableListOf("file1", "file2", "file3")
//                val sublist2 = mutableListOf("file4", "file5", "file6")
//                GlobalVariables.myFolderList1.add(sublist1)
//                GlobalVariables.myFolderList1.add(sublist2)

                setFolderLists(
                    GlobalVariables.folderLists,
                    GlobalVariables.folderNames,
                    GlobalVariables.fileLists_above,
                    GlobalVariables.fileNames_above
                );

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
//                    Greeting("Page A")
                    MyApp(modifier = Modifier.fillMaxSize(),
                        folderLists = GlobalVariables.folderLists,
                        folderNames = GlobalVariables.folderNames,
                        fileLists_above = GlobalVariables.fileLists_above,
                        fileNames_above = GlobalVariables.fileNames_above,
                        )
                }
            }
        }
    }
}

@Composable
fun FItem(name: String, name2: String, modifier: Modifier = Modifier,
                 backColor: Color = Color(0xFFFFD5B6),
          button1_OnClicked: () -> Unit, a2:Int=0) {
    val expanded = remember { mutableStateOf(false) }
    Surface(
        color = backColor,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "$name2:",
                    fontSize = 10.sp,
                    lineHeight = 50.sp,
                    color = Color.Gray
                )
                Text(
                    text = "$name",
                    lineHeight = 20.sp,
                )
            }
            ElevatedButton(
                onClick =  button1_OnClicked// 将参数传递给 button1_OnClicked
            ) {
                Text(
                    "get in",
                    color = Color.DarkGray
                )
            }
        }
    }
}
@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    folderLists: MutableList<File> = mutableListOf(),
    folderNames: MutableList<String> = mutableListOf(),
    fileLists_above: MutableList<MutableList<File>> = mutableListOf(),
    fileNames_above: MutableList<MutableList<String>> = mutableListOf(),
    ) {
    var showWhatPage by remember { mutableStateOf(0) }
    var a by remember { mutableStateOf(0) }
    Surface(modifier) {
        if (showWhatPage == 0) {
            folderLists(
                folderOnClicked = { index ->
                    showWhatPage = 1
                    a = index
                },
                folderNames = folderNames,
            )
        } else if (showWhatPage == 1){
            fileLists(
                fileOnClicked = {},
                previewOnClicked = {
                    showWhatPage=0
                },
                index_fileNames_above = a,
                fileNames_above = fileNames_above,
                )
        } else{

        }
    }
}
@Composable
fun folderLists(
    modifier: Modifier = Modifier,
    folderNames: MutableList<String> = mutableListOf(),
    folderOnClicked: (Int) -> Unit,
    ) {
    Column {
        Row(
            horizontalArrangement = Arrangement.End
        ){
            Text(
                text = "my folders",
                fontSize = 50.sp,
                lineHeight = 50.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold // 設置粗體
            )
        }

        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            itemsIndexed(items = folderNames) {
                    index, sublist -> //
                FItem(name = sublist, name2="folder",
                    backColor = Color(0xEBD8C5FF),
                    button1_OnClicked = {
                        folderOnClicked(index)}
                )
            }
        }
    }
}

@Composable
fun fileLists(
    modifier: Modifier = Modifier,
    //     names: List<String> = List(5) { "$it" },
    fileNames_above: MutableList<MutableList<String>> = mutableListOf(),
    index_fileNames_above: Int,
    fileOnClicked: (index:Int) -> Unit,
    previewOnClicked: () -> Unit = {},
    ) {
    var names: MutableList<String> = mutableListOf();
    names = fileNames_above.get(index_fileNames_above);

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){ // 最上方標題區域
            ElevatedButton(
                onClick = previewOnClicked
            ) {
                Text("preview",
                    color = Color.DarkGray
                )
            }
            Text(
                text = "my files",
                fontSize = 50.sp,
                lineHeight = 50.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            itemsIndexed(items = names) {
                    index, sublist ->
                        FItem(
                            name = sublist,
                            name2 = "file",
                            backColor = Color(0xFFFFF4E9),
                            button1_OnClicked = { fileOnClicked(index) }
                        )
            }
        }
    }
}
@Composable
fun setFolderLists(
     folderLists: MutableList<File>,
     folderNames: MutableList<String>,
     fileLists_above: MutableList<MutableList<File>>,
     fileNames_above: MutableList<MutableList<String>>
){
    var folderLists_t: Array<File>;
    var fileLists_t: Array<File>;
    var fileLists_t2: MutableList<File> = mutableListOf();
    var fileNames_t: MutableList<String> = mutableListOf();
    folderLists.clear(); folderNames.clear();
    fileLists_above.clear(); fileNames_above.clear();

    // myDatas = myDatas
    val myDatas = File("/myData");
//    val myDatas = File("/misstest");

    if(myDatas.exists() && myDatas.isDirectory){
        folderLists_t = myDatas.listFiles(); // folderLists_t = [mavis, candy]
        for(item in folderLists_t){ // item = mavis, candy
            folderLists.add(item); // get folderLists
            folderNames.add(item.name); // get folderNames
            fileLists_t = item.listFiles(); // fileLists_t = [mavisA, mavisB]
            for(item2 in fileLists_t){ // item2 = mavisA, mavisB
                fileLists_t2.add(item2);  // fileLists_t2 = [mavisA, mavisB] (所需type)
                fileNames_t.add(item2.name);  // fileNames_t = [mavisA, mavisB] (所需type)
            } // for
            fileLists_above.add(fileLists_t2);// get fileNames_above
            fileNames_above.add(fileNames_t); // get fileLists_above
            fileNames_t.clear(); // clear fileNames_t
        } // for
    } // if
    else{
        GlobalVariables.folderNames.add("misstest 1")
        GlobalVariables.folderNames.add("misstest 2")
        val sublist1 = mutableListOf("miss1", "miss2", "miss3")
        val sublist2 = mutableListOf("miss4", "miss5", "miss6")
        GlobalVariables.fileNames_above.add(sublist1)
        GlobalVariables.fileNames_above.add(sublist2)
    }

}




