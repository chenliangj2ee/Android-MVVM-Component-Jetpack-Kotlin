package com.chenliang.baselibrary.utils

/**
 * author:chenliang
 * date:2021/11/11
 */
object BusCode {
    const val EXIT = 0//退出应用
    const val SAVE_LIST_TAGS_SUCCESS = 1000//保存情感标签后，通知之前3个界面finish
    const val REFRESH_MINE_USER_INFO = 1001//收藏/取消收藏后，通知我的界面刷新
    const val PAYMENT_SUCCESS = 1002//购买确认后，课程详情页刷新
    const val FULL_SCREEN = 1003//购买确认后，课程详情页刷新
    const val REFRESH_LINK_MIC_LIST = 1004//购买确认后，课程详情页刷新
    const val RESULT_EXPERT_STUDIO_USER_EDIT = 1005//专家装修工作室，修改个人信息
    const val LOGIN_SUCCESS = 1006//验证码登录成功
    const val GET_USERSIG_SUCCESS = 1007//获取IM的token成功
    const val RESULT_ADD_ZGZS = 1008//专家装修工作室，修改个人信息
    const val RESULT_TIME_SETTINGS = 1009//专家装修工作室，修改个人信息
    const val REFRESH_CONSULT_LIST = 1010//刷新咨询列表
    const val VIP_CARD_USEORCANLE = 1011//VIP会员卡取消或选中
    const val IM_UNREADNUM_CHANGE = 1012//IM未读消息数改变
    const val CONVERSATION_ITEM_NUM = 1013//IM未读消息数改变
    const val ORDER_REFRESH = 1015//IM准备离线推送
    const val IM_FIST_LOGIN_SUCCESS = 1016//IM第一次登录成功刷新

    const val IM_LOGIN_ERROR = 1017//IM准备离线推送
    const val PAYMENT_RESULT = 1023//微信购买完获取订单状态
    const val PAYMENT_FAILURE = 1024//购买确认失败

    const val MAIN_IN = 1018//进入主页
    const val MAIN_OUT = 1019//退出主页
    const val SPLASH_IN = 1020//启动
    const val MAIN_RESUME = 1021//进入主页
    const val IM_GOTO_CHAT = 1022//进入主页

    const val INIT = 1023//进入主页

    const val AUTH_FINISH = 1024//进入主页
    const val STUDIO_EDIT_SUCCESS = 1025//进入主页
    const val RECHARGE_SUCCESS = 1026//充值成功返回我的钱包页
    const val RESULT_DELETE_ZGZS = 1027//专家装修工作室，修改个人信息
    const val UPDATE_EXPERT_INFO = 1028//专家装修工作室，修改个人信息

    const val REFRESH_EXPERT_EDIT = 1029//更新专家修改信息

    const val LIVE_INTO_1 = 1030//主播进入
    const val LIVE_INTO_2 = 1031//粉丝进入

    const val LIVE_GET_RTM_TOKEN = 1032//粉丝进入
    const val LIVE_JOIN = 1033

    const val GOTO_IMCHAT = 1034//去聊天
    const val GOTO_IMVIDEO = 1035//去视频
    const val GOTO_IMVOICE = 1036//去语音

    const val IM_LOGINFAILE = 1037//im登录失败
    const val TODO_REFRESH = 1038//im登录失败

    const val GOTO_SENDUSERRTCTOKEN = 1039//获取C端发送方rtctoken
    const val GOTO_BRTCTOKEN = 1040//获取B端rtctoken


    const val LIVE_GET_RTCTOKEN = 1041//获取C端RTC token

    const val COURSE_PLAY = 1042//课程播放
    const val LIVE_DRAW_INVITE_MESSAGE = 1043//主播发起画板邀请
    const val LIVE_DRAW_ACCEPT_INVITE = 1044//观众接受画板邀请
    const val LIVE_DRAW_REJECT_INVITE = 1045//观众拒绝画板邀请

    const val LIVE_DRAW_SHOW_ZB = 1046//主播显示画板
    const val LIVE_DRAW_SELECTED_USER_FINISH = 1047//画板选择用户结束
    const val LIVE_DRAW_SHOW_GZ = 1048//主播显示画板
    const val LIVE_DRAW_CLOSE_ALL = 1049//主播显示画板
    const val LIVE_UPDATE_ZHUBO_UID = 1050//主播显示画板
    const val UPDATE_IM_USERINFO = 1051//修改IM信息

    const val RESULT_ADD_TRAIN = 1052//专家装修工作室，增加培训经历
    const val RESULT_DELETE_TRAIN = 1053//专家装修工作室，删除培训经历

    const val RELAX_FAVORITE = 1054//收藏/取消收藏后，通知我的界面刷新
    const val RELAX_PLAY_STATUS = 1055//播放状态变化
    const val MUSIC_PLAY_NEXT = 1056//播放切换
    const val CHANGE_COLUMN_ID = 1057//切换专栏

    const val LIVE_MUSIC_PLAY = 1058//音乐播放
    const val LIVE_MUSIC_PLAY_END = 1059//当前音乐播放结束
    const val LIVE_MUSIC_PLAY_CLOSE = 1060//关闭直播背景音乐
    const val LIVE_MUSIC_PLAY_PAUSE = 1061//暂停播放
    const val LIVE_MUSIC_PLAY_RESUME = 1062//继续播放
    const val LIVE_MUSIC_PLAY_NEXT = 1063//播放下一首
    const val LIVE_MUSIC_PLAY_MODE_CHANGE = 1064//暂停播放

    const val LIVE_COURSEWARE_SHOW = 1065//展示课件
    const val LIVE_COURSEWARE_OPEN_EVENT = 1066//打开课件
    const val LIVE_COURSEWARE_CLOSE_EVENT = 1067//打开课件
    const val LIVE_COURSEWARE_SWITCH_EVENT = 1068//课件切换课件页

    const val LIVE_CLOSE_POPUP = 1069//关闭座位popup
    const val LIVE_TEST_INVITE = 1070//直播测评邀请

    const val LIVE_USER_TEST_FINISH= 1071//用户完成测评信通知直播间关联直播与测评
    const val LIVE_SHOW_TEST_VIEW= 1072//显示测评View
    const val LIVE_REFRESH_TEST_RESULT= 1073//用户发送信令告诉主播直播间测评报告刷新
}