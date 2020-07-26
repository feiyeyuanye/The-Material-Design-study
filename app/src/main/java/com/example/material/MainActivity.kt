package com.example.material

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val fruits = mutableListOf(Fruit("Apple", R.drawable.apple),
        Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange),
        Fruit("Watermelon", R.drawable.watermelon),
        Fruit("Pear", R.drawable.pear),
        Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pineapple),
        Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry),
        Fruit("Mango", R.drawable.mango))

    val fruitList = ArrayList<Fruit>()
    lateinit var adapter: FruitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
    }

    private fun initData() {
        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        adapter = FruitAdapter(this,fruitList)
        recyclerView.adapter = adapter
    }

    private fun initFruits() {
        fruitList.clear()
        repeat(50){
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    private fun initView() {
        // 调用 setSupportActionBar 并将 Toolbar 的实例传入。
        // 这样，既使用了 Toolbar，又让它的外观与功能都和 ActionBar 一致了。
        setSupportActionBar(toolbar)

        // 为滑动菜单添加导航按钮
        // 调用 getSupportActionBar() 得到 ActionBar 的实例，虽然这个 ActionBar 的具体实现是由 Toolbar 实现的。
        supportActionBar?.let {
            // 当 ActionBar 不为空时，进行如下操作。
            // 将导航按钮显示出来
            it.setDisplayHomeAsUpEnabled(true)
            // 设置导航按钮的图标。
            // 实际上，Toolbar 最左侧的这个按钮就叫做 Home 按钮，
            // 它默认的图标是一个返回的箭头，含义是返回上一个 Activity，这里将它默认的样式和功能都进行了修改。
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        // 为 NavigationView 的菜单项处理点击事件
        navView.setCheckedItem(R.id.navCall)
        // 设置一个菜单项选中事件的监听器
        navView.setNavigationItemSelectedListener {
            // 在这里处理菜单项选中的处理逻辑
            // 将滑动菜单关闭，并返回 true 表示此事件已被处理。
            drawerLayout.closeDrawers()
            true
        }

        // 为悬浮按钮处理点击事件，和普通的 Button 其实没什么两样。
        fab.setOnClickListener{ view ->
            // 可交互式提示工具
            // 调用 make() 创建一个 Snackbar 对象。
            // 第一个参数只要是当前界面布局的任意一个 View 都可以，Snackbar 会使用这个 View 自动查找最外层的布局，用于展示提示信息。
            // 调用 setAction() 设置一个动作
            Snackbar.make(view,"Data deleted",Snackbar.LENGTH_SHORT).setAction("Undo"){
                Toast.makeText(this,"Data restored",Toast.LENGTH_SHORT).show()
            }.show()
        }

        // 设置下拉刷新进度条的颜色
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        // 设置下拉刷新的监听器
        swipeRefresh.setOnRefreshListener {
            refreshFruits(adapter)
        }
    }

    private fun refreshFruits(adapter: FruitAdapter) {
        thread {
            // 本地刷新操作速度非常快，这里为了看到刷新效果，所以将线程沉睡两秒。
            Thread.sleep(2000)
            // 切换回主线程
            runOnUiThread{
                initFruits()
                adapter.notifyDataSetChanged()
                // 调用 setRefreshing() 并传入 false，表示刷新事件结束，并隐藏刷新进度条。
                swipeRefresh.isRefreshing = false
            }
        }
    }

    /**
     * 在此方法中加载了 toolbar.xml 这个菜单文件
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    /**
     * 处理各个按钮的点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            // 对 Home 按钮的点击事件进行处理，它的 id 永远是 android.R.id.home。
            // 调用 DrawerLayout 的 openDrawer() 将滑动菜单展示出来，这里参数指定了和 XML 中的一致。
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
            R.id.backup ->
                Toast.makeText(this,"You clicked Backup", Toast.LENGTH_SHORT).show()
            R.id.delete ->
                Toast.makeText(this,"You clicked Delete", Toast.LENGTH_SHORT).show()
            R.id.settings ->
                Toast.makeText(this,"You clicked Settings", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
