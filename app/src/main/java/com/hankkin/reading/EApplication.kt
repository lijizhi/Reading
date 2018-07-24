package com.hankkin.reading

import android.app.Application
import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import com.bilibili.magicasakura.utils.ThemeUtils
import com.hankkin.reading.utils.FileUtils
import com.hankkin.library.utils.SPUtils
import com.hankkin.reading.common.Constant
import com.hankkin.reading.greendao.DaoMaster
import com.hankkin.reading.greendao.DaoSession
import com.hankkin.reading.utils.ThemeHelper
import com.hankkin.reading.utils.ThemeHelper.*

/**
 * Created by huanghaijie on 2018/5/18.
 */
class EApplication : Application() ,ThemeUtils.switchColor{


    companion object {
        private var instance: EApplication? = null

        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        FileUtils.initSd()
        SPUtils.init(this)
        ThemeUtils.setSwitchColor(this)
        initDao()
    }

    fun initDao(){
        val devOpenHelper = DaoMaster.DevOpenHelper(this,Constant.DB.DB_NAME,null)
        val daoMaster = DaoMaster(devOpenHelper.writableDb)
        val daoSession = daoMaster.newSession()
    }

    override fun replaceColorById(context: Context,  @ColorRes colorId: Int): Int {
        if (ThemeHelper.isDefaultTheme(context)) {
            return context.resources.getColor(colorId)
        }
        val theme = getTheme(context)
        var colorIdTemp = colorId
        if (theme != null) {
            colorIdTemp = getThemeColorId(context, colorId, theme)
        }
        return context.resources.getColor(colorIdTemp)
    }

    override fun replaceColor(context: Context, @ColorInt originColor: Int): Int {
        if (ThemeHelper.isDefaultTheme(context)) {
            return originColor
        }
        val theme = getTheme(context)
        var colorId = -1

        if (theme != null) {
            colorId = getThemeColor(context, originColor.toLong(), theme)
        }
        return if (colorId != -1) resources.getColor(colorId) else originColor
    }

    private fun getTheme(context: Context): String? {
        return when(ThemeHelper.getTheme(context)){
            COLOR_YIMA -> "yima"
            COLOR_KUAN -> "kuan"
            COLOR_BILI -> "bili"
            COLOR_YIDI -> "yidi"
            COLOR_SHUIYA -> "shuiya"
            COLOR_YITENG -> "yiteng"
            COLOR_JILAO -> "jilao"
            COLOR_ZHIHU -> "zhihu"
            COLOR_GUTONG -> "gutong"
            COLOR_DIDIAO -> "didiao"
            COLOR_GAODUAN -> "gaoduan"
            COLOR_APING -> "aping"
            COLOR_LIANGBAI -> "liangbai"
            COLOR_ANLUOLAN -> "anluolan"
            COLOR_XINGHONG -> "xinghong"
            else -> {
                "yima"
            }
        }
    }

    private
    @ColorRes
    fun getThemeColorId(context: Context, colorId: Int, theme: String): Int {
        when (colorId) {
            R.color.theme_color_primary -> return context.resources.getIdentifier(theme, "color", packageName)
            R.color.theme_color_primary_dark -> return context.resources.getIdentifier(theme+"_dark", "color", packageName)
            R.color.colorAccent -> return context.resources.getIdentifier(theme+"_accent", "color", packageName)
        }
        return colorId
    }
    private
    @ColorRes
     fun getThemeColor(context: Context, color: Long, theme: String): Int {
        when (color) {
            0xfff44336 -> return context.resources.getIdentifier(theme, "color", packageName)
            0xfff44336 -> return context.resources.getIdentifier(theme+"_dark", "color", packageName)
            0xfff44336 -> return context.resources.getIdentifier(theme+"_accent", "color", packageName)
        }
        return -1
    }
}