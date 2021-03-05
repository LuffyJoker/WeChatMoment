package com.peng.wechatmoment.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.peng.wechatmoment.R
import java.util.*

/**
 * 底部功能弹窗
 */
class ActionSheetDialog {

    private var context: Context
    private lateinit var dialog: Dialog
    private lateinit var txtTitle: TextView
    private lateinit var txtCancel: TextView
    private lateinit var layoutContent: LinearLayout
    private lateinit var scrollView: ScrollView
    private var showTitle = false
    private var sheetItemList: MutableList<SheetItem> = mutableListOf()
    private var display: Display

    constructor(context: Context) {
        this.context = context
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        display = windowManager.defaultDisplay
    }

    fun builder(): ActionSheetDialog {
        // 获取 Dialog 布局
        val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_actionsheet, null)
        // 设置Dialog最小宽度为屏幕宽度
        view.minimumWidth = display.width
        // 获取自定义Dialog布局中的控件
        scrollView = view.findViewById<View>(R.id.sLayout_content) as ScrollView
        layoutContent = view.findViewById<View>(R.id.lLayout_content) as LinearLayout
        txtTitle = view.findViewById<View>(R.id.txt_title) as TextView
        txtCancel = view.findViewById<View>(R.id.txt_cancel) as TextView
        txtCancel.setOnClickListener { dialog.dismiss() }
        // 定义Dialog布局和参数
        dialog = Dialog(context, R.style.ActionSheetDialogStyle)
        dialog.setContentView(view)
        val dialogWindow = dialog.window
        dialogWindow!!.setGravity(Gravity.LEFT or Gravity.BOTTOM)
        val lp: WindowManager.LayoutParams = dialogWindow.attributes
        lp.x = 0
        lp.y = 0
        dialogWindow.attributes = lp
        return this
    }

    fun setTitle(title: String): ActionSheetDialog {
        showTitle = true
        txtTitle.visibility = View.VISIBLE
        txtTitle.text = title
        return this
    }

    fun setCancelable(cancel: Boolean): ActionSheetDialog {
        dialog.setCancelable(cancel)
        return this
    }

    fun setCanceledOnTouchOutside(cancel: Boolean): ActionSheetDialog {
        dialog.setCanceledOnTouchOutside(cancel)
        return this
    }

    /**
     *
     * @param strItem
     *            条目名称
     * @param color
     *            条目字体颜色，设置null则默认蓝色
     * @param listener
     * @return
     */
    fun addSheetItem(strItem: String, color: SheetItemColor, listener: OnSheetItemClickListener): ActionSheetDialog {
        sheetItemList.clear()
        sheetItemList.add(SheetItem(strItem, color, listener))
        return this
    }

    /** 设置条目布局 */
    private fun setSheetItems() {
        if (sheetItemList.size == 0) {
            return
        }
        val size = sheetItemList.size
        // 添加条目过多的时候控制高度
        if (size >= 7) {
            val params = scrollView.layoutParams as LinearLayout.LayoutParams
            params.height = display.height / 2
            scrollView.layoutParams = params
        }
        // 循环添加条目
        for (i in 1..size) {
            val sheetItem = sheetItemList[i - 1]
            val strItem = sheetItem.name
            val color = sheetItem.color
            val textView = TextView(context)
            textView.text = strItem
            textView.textSize = 18f
            textView.gravity = Gravity.CENTER
            // 背景图片
            if (size == 1) {
                if (showTitle) {
                    textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector)
                } else {
                    textView.setBackgroundResource(R.drawable.actionsheet_single_selector)
                }
            } else {
                if (showTitle) {
                    if (i in 1 until size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector)
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector)
                    }
                } else {
                    when {
                        i == 1 -> {
                            textView.setBackgroundResource(R.drawable.actionsheet_top_selector)
                        }
                        i < size -> {
                            textView.setBackgroundResource(R.drawable.actionsheet_middle_selector)
                        }
                        else -> {
                            textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector)
                        }
                    }
                }
            }
            // 字体颜色
            textView.setTextColor(Color.parseColor(SheetItemColor.Blue.name))
            // 高度
            val scale = context.resources.displayMetrics.density
            val height = (45 * scale + 0.5f).toInt()
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height
            )
            // 点击事件
            textView.setOnClickListener {
                sheetItem.itemClickListener.onClick(i)
                dialog.dismiss()
            }
            scrollView.addView(textView)
        }
    }

    fun show() {
        setSheetItems()
        dialog.show()
    }

    interface OnSheetItemClickListener {
        fun onClick(which: Int)
    }

    class SheetItem(
        internal var name: String,
        internal var color: SheetItemColor,
        internal var itemClickListener: OnSheetItemClickListener
    )

    enum class SheetItemColor(name: String) {
        Blue("#999999"), Red("#FD4A2E");
    }
}