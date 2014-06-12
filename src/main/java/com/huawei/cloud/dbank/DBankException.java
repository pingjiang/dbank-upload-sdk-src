package com.huawei.cloud.dbank;

/**
 * Created by pingjiang on 14-6-11.
 * <p/>
 * 网盘的异常
 * <p/>
 * <p/>
 * 1. 错误类型
 * 1 连接层面：接口在连接层面使用HTTP协议接入，HTTP STATUS表示连接层面的状态；
 * 2 平台层面：HTTP Response Header中如果有NSP_STATUS，且该值不为0，则表示开放平台部分捕获到的错误；
 * 3 服务层面：应用自己在返回结果中定义的，这种其实是属于正常结果的一部分，每个接口单独定义，且调用者需要自己处理；
 * 其中，服务可以直接抛出异常，被平台所捕获，从而转化为NSP_STATUS。
 * 2. 错误处理机制
 * 调用服务时的错误处理机制建议为：
 * 0 检测HTTP STATUS，看是否正常
 * 1 检测NSP_STATUS ，看是否正常
 * 2 根据调用服务的说明，看返回值中是否有错误
 * 3. HTTP STATUS描述
 * 异常码
 * 描述
 * 200	成功
 * 401	需要鉴权
 * 500	开放网关发生异常
 * 502~504	其他网络错误
 * 4. NSP STATUS描述
 * 异常码
 * 描述
 * 0	成功
 * 1	一个未知的错误发生
 * 2	服务临时不可用
 * 3	未知的方法
 * 4	应用已达到设定的请求上限
 * 5	请求来自未经授权的IP地址
 * 6	当前用户session key过期了
 * 7	调用次数超过了限制
 * 8	调用太频繁
 * 100	无效未知参数
 * 101	无效的API_KEY
 * 102	无效的SESSION_KEY
 * 103	必须是POST提交
 * 104	无效的签名
 * 105	缺少系统参数：如nsp_svc, nsp_ts, nsp_key等
 * 106	app或者session没有调用当前服务的权限
 * 107	client和secret需要重新获取（如算法升级等）
 * 108	路由失败，无法识别服务投递地址
 * 109	nsp_ts偏差过大
 * 110	接口内部异常
 * 403	无权限
 * <p/>
 * 异常码
 * 来源
 * 描述
 * 0		成功
 * 1		未知错误
 * 2		服务临时不可用
 * 3		未知方法
 * 4		应用已达到设定的请求上限
 * 5		请求来自未经授权的IP地址
 * 6		请求session过期
 * 7		调用太频繁
 * 100		无效未知参数
 * 101		无效的API_KEY
 * 102		无效的SESSION_KEY
 * 103		必须是POST提交
 * 104		无效签名
 * 105		缺少系统参数：如nsp_svc, nsp_ts, nsp_key等
 * 106		pp或者session没有调用当前服务的权限
 * 108		路由失败，无法识别服务投递地址
 * 109		nsp_ts偏差过大
 * 110		接口内部异常
 * 403		当前应用或用户无权限访问该接口。如要开通权限，请联系管理员
 * 451		发送邮件失败，或者加入邮件发送队列失败
 * 452		发送短信失败，或者加入短信发送队列失败
 * 501		用户名或密码错误
 * 502		创建Session异常
 * 503		无效的tpl参数
 * 504		用户未激活
 * 505		用户被限制登录
 * 510		激活异常
 * 550		接口参数缺少或错误
 * 551		验证码错误
 * 600		请求中包含敏感词
 * 601		请求中包含非法的文件
 * 550		接口参数缺少或错误
 * 9001	nsp.vfs	初始化vfs失败
 * 9002	nsp.vfs	读取用户信息失败
 * 9003	nsp.vfs	参数错误
 * 9004	nsp.vfs	应用访问目录权限受限，无权访问目录
 * 9005	nsp.vfs	源文件路径不存在
 * 9006	nsp.vfs	目标路径不存在
 * 9007	nsp.vfs	初始化用户内部错误
 * 9008	nsp.vfs	 internal error 内部错误
 * 9009	nsp.vfs	文件或文件夹属性不存在
 * 9010	nsp.vfs	服务器内存错误
 * 9011	nsp.vfs	文件版本错误
 */
public class DBankException extends Throwable {
    public DBankException(String msg) {
        super(msg);
    }

    public DBankException(Throwable e) {
        super(e);
    }

    public DBankException(String msg, Throwable e) {
        super(msg, e);
    }
}
