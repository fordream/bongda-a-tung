package com.app.bongda.callback;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler extends DefaultHandler
{

    private final String BANG_XEP_HANG_RESULT = "wsFootBall_BangXepHangResult";
    private final String LIVE_RESULT = "wsFootBall_LivesResult";
    private final String LIVE_THEO_GIAI = "wsFootBall_Lives_Theo_GiaiResult";
    private final String NHAN_DINH_CHUYEN_GIA_TRAN = "wsFootBall_Nhan_Dinh_Chuyen_Gia_Theo_TranResult";
    private final String PHONGDO_THEO_TRAN = "wsFootBall_Phong_DoResult";
    private final String QUOCGIA_DETAIL_TAG = "wsFootBall_Giai_Theo_QuocGiaResult";
    private final String QUOCGIA_RESULT_TAG = "wsFootBall_QuocgiaResult";
    
    private final String REGISTER_RESULT = "wsUsers_RegisterResult";
    private final String LOGIN_RESULT = "wsUsers_LoginResult";
    private final String NAPTHE_RESULT = "wsUsers_NapTheResult";
    
    StringBuilder builder;
    String result;

    public XmlHandler()
    {
    }

    public void characters(char ac[], int i, int j)
        throws SAXException
    {
        super.characters(ac, i, j);
        if (builder == null)
        {
            builder = new StringBuilder();
        }
        builder.append(ac, i, j);
    }

    public void endElement(String s, String s1, String s2)
        throws SAXException
    {
        super.endElement(s, s1, s2);
        if (( s1.equals(REGISTER_RESULT) || s1.equals(LOGIN_RESULT) || s1.equals(NAPTHE_RESULT) || s1.equals("wsFootBall_Giai_Theo_QuocGia_LiveResult") || s1.equals("wsFootBall_Quocgia_LiveResult") || s1.equals("wsFootBall_Lives_Co_GameDuDoanResult") || s1.equals("wsFootBall_Phong_Do_ChiTietResult") || s1.equals("wsFootBall_Lives_TyLeDuDoanResult") || s1.equals("wsFootBall_Nhan_Dinh_Chuyen_GiaResult") || s1.equals("wsFootBall_ChiTiet_TranResult") || s1.equals("wsFootBall_QuocgiaResult") || s1.equals("wsFootBall_Giai_Theo_QuocGiaResult") || s1.equals("wsFootBall_BangXepHangResult") || s1.equals("wsFootBall_LivesResult") || s1.equals("wsFootBall_Lives_Theo_GiaiResult") || s1.equals("wsFootBall_Phong_DoResult") || s1.equals("wsFootBall_Nhan_Dinh_Chuyen_Gia_Theo_TranResult")) && builder != null)
        {
            result = builder.toString();
            builder = null;
        }
    }

    public String getResult()
    {
        return result;
    }

    public void startDocument()
        throws SAXException
    {
        super.startDocument();
    }

    public void startElement(String s, String s1, String s2, Attributes attributes)
        throws SAXException
    {
        super.startElement(s, s1, s2, attributes);
        if (( s1.equals(REGISTER_RESULT) || s1.equals(LOGIN_RESULT) || s1.equals(NAPTHE_RESULT) || s1.equals("wsFootBall_Giai_Theo_QuocGia_LiveResult") || s1.equals("wsFootBall_Quocgia_LiveResult") || s1.equals("wsFootBall_Lives_Co_GameDuDoanResult") || s1.equals("wsFootBall_Phong_Do_ChiTietResult") ||s1.equals("wsFootBall_Lives_TyLeDuDoanResult") || s1.equals("wsFootBall_Nhan_Dinh_Chuyen_GiaResult") || s1.equals("wsFootBall_ChiTiet_TranResult") || s1.equals("wsFootBall_QuocgiaResult") || s1.equals("wsFootBall_Giai_Theo_QuocGiaResult") || s1.equals("wsFootBall_BangXepHangResult") || s1.equals("wsFootBall_LivesResult") || s1.equals("wsFootBall_Lives_Theo_GiaiResult") || s1.equals("wsFootBall_Phong_DoResult") || s1.equals("wsFootBall_Nhan_Dinh_Chuyen_Gia_Theo_TranResult")) && builder != null)
        {
            result = builder.toString();
            builder = null;
        }
    }
}
