/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HMIS;

import cms.access.Access_User;
import cms.tools.Js;
import cms.tools.Server;
import cms.tools.jjTools;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.DefaultTableModel;
import jj.jjCalendar_IR;
import jj.jjDatabase;
import jj.jjDatabaseWeb;
import jj.jjNumber;

/**
 *
 * @author shohreh.shiran Date 1397.11.1
 */
public class Commettes {

    public static String tableName = "hmis_commettes";
    public static String _id = "id";
    public static String _isActive = "commettes_isActive";//وضعیت فعال وغیر فعال
    public static String _creatorId = "commettes_creatorId";//عنوان کمیته
    public static String _title = "commettes_title";//عنوان کمیته
    public static String _superwizar = "commettes_superwizar";//رئیس کمیته
    public static String _secretary = "commettes_secretary";// دبیر کمیته
    public static String _dateOfHoldingMeeting = "commettes_dateOfHoldingMeeting";//برگزاری جلسات
    public static String _members = "commettes_members";//اعضای کمیته  همان نقش میشود از جدول نقش ها می اید 
    public static String _regulationFile = "commettes_regulationFile";//آئین نامه
    public static String _documnetsFile = "commettes_documentsFile";//داکیومنت
    public static String _description = "commettes_description";//توضیحات

    public static int rul_rfs = 0;
    public static int rul_ins = 0;
    public static int rul_edt = 0;
    public static int rul_dlt = 0;
    public static int rul_lng2 = 0;
    public static int rul_lng3 = 0;
    public static int rul_lng4 = 0;
    public static int rul_lng5 = 0;
    public static String lbl_insert = "ذخیره";
    public static String lbl_delete = "حذف";
    public static String lbl_edit = "ویرایش";

    public static String refresh(HttpServletRequest request, HttpServletResponse response, jjDatabaseWeb db, boolean needString) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_rfs);
            if (!hasAccess.equals("")) {
                Server.outPrinter(request, response, hasAccess);
                return "";
            }
            StringBuilder html = new StringBuilder();
            StringBuilder html3 = new StringBuilder();
            StringBuilder html4 = new StringBuilder();
            StringBuilder html5 = new StringBuilder();
            StringBuilder html6 = new StringBuilder();
            DefaultTableModel dtm = db.Select(tableName);
            List<Map<String, Object>> row = jjDatabase.separateRow(dtm);

            html.append("        <div class=\"card-header bg-primary tx-white\">لیست کمیته ها و کارگروه ها</div>\n");
            html.append(" <div class=\"card-body pd-sm-30\">\n"
                    + "                                        <p class=\"mg-b-20 mg-sm-b-30\">\n"
                    + "                                            <a style='color:#fff' class=\"btn btn-success pd-sm-x-20 mg-sm-r-5 tx-white\" onclick=\"hmisCommettes.m_add_new();\" >کمیته جدید</a>\n"
                    + "                                        </p>\n"
                    + "                                    </div>");
            html.append("        <div class=\"table-wrapper\">\n");
            html.append("<table id='refreshCommettes' class='table display responsive' class='tahoma10' style='direction: rtl;width:982px'><thead>");
            html.append("<th class='r' width='5%'>کد</th>");
            html.append("<th class='r' width='20%'>نام کمیته</th>");
            html.append("<th class='r' width='20%'>دبیر کمیته</th>");
            html.append("<th class='r' width='20%'>رئیس کمیته</th>");
            html.append("<th class='r' width='20%'>دعوتنامه</th>");
            html.append("<th class='r' width='20%'>عملیات</th>");
            html.append("</thead><tbody>");
            for (int i = 0; i < row.size(); i++) {
                List<Map<String, Object>> secretaryTitle = jjDatabase.separateRow(db.Select(Role.tableName, Role._id + "=" + row.get(i).get(_secretary)));
                List<Map<String, Object>> superwizerTitle = jjDatabase.separateRow(db.Select(Role.tableName, Role._id + "=" + row.get(i).get(_superwizar)));
                html.append("<tr  class='mousePointer'>");
                html.append("<td class='c'>" + row.get(i).get(_id) + "</td>");
                html.append("<td class='r'>" + row.get(i).get(_title) + "</td>");
                html.append("<td class='r'>" + secretaryTitle.get(0).get(Role._title) + "</td>");
                html.append("<td class='r'>" + superwizerTitle.get(0).get(Role._title) + "</td>");
                html.append("<td class='r'><i class='icon ion-email' onclick='hmisCommettes.showInvitationForm(" + row.get(i).get(_id) + ");' style='color:#ffcd00!important'></i></td>");
                html.append("<td class='r'><i class='icon ion-ios-gear-outline'  onclick='hmisCommettes.m_select(" + row.get(i).get(_id) + ")'></i></td>");
                html.append("</tr>");
            }
            html.append("</tbody></table>");
            html.append("</div>");
            String height = jjTools.getParameter(request, "height");
            String panel = jjTools.getParameter(request, "panel");
            if (!jjNumber.isDigit(height)) {
                height = "400";
            }
            if (panel.equals("")) {
                panel = "swCommettesTbl";
            }
            String html2 = Js.setHtml("#" + panel, html.toString());

            html2 += Js.table("#refreshCommettes", "300", 0, "", "کمیته ها");
            //////////////////////////////برای سلکت های موجود در فرم کمیتهها/////////////
            List<Map<String, Object>> roleRow = jjDatabase.separateRow(db.Select(Role.tableName, Role._condition + "='active'"));
            html3.append("<select required class=\"form-control\" id=\"commettes_secretary\" name=\"commettes_secretary\" tabindex='-1'>");
            html3.append("<option value=''>انتخاب کنید</option>");
            for (int i = 0; i < roleRow.size(); i++) {
                html3.append("<option value='" + roleRow.get(i).get(Role._id) + "'>" + roleRow.get(i).get(Role._title) + "</option>");
            }
            html3.append("</select>");
            html4.append("<select required class=\"form-control\" id=\"commettes_superwizar\" name=\"commettes_superwizar\" tabindex='-1'>");
            html4.append("<option value=''>انتخاب کنید</option>");
            for (int i = 0; i < roleRow.size(); i++) {
                html4.append("<option value='" + roleRow.get(i).get(Role._id) + "'>" + roleRow.get(i).get(Role._title) + "</option>");
            }
            html4.append("</select>");
            String script = Js.setHtml("#selectOptionRoleOne", html3);
            script += Js.setHtml("#selectOptionRoleTwo", html4);
            ///////////////////////////////////////////////نمایش جدول نقش ها

            html5.append("        <div class=\"table-wrapper\">\n");
            html5.append("<table required id='refreshRoles' class='table display responsive' class='tahoma10' style='direction: rtl;'><thead>");
            html5.append("<th class='r' width='20%'>کد</th>");
            html5.append("<th class='r' width='20%'>سمت</th>");
            html5.append("<th class='r' width='40%'>نام و نام خانوادگی</th>");
            html5.append("<th class='r' width='20%'>عملیات</th>");
            html5.append("</thead><tbody>");
            for (int i = 0; i < roleRow.size(); i++) {

                List<Map<String, Object>> userRow = jjDatabase.separateRow(db.Select(Access_User.tableName, Access_User._id + "=" + roleRow.get(i).get(Role._user_id)));
                html5.append("<tr  class='mousePointer'>");
                html5.append("<td class='r'>" + roleRow.get(i).get(Role._id) + "</td>");
                html5.append("<td class='r'>" + roleRow.get(i).get(Role._title) + "</td>");
                html5.append("<td class='r'>" + userRow.get(0).get(Access_User._name) + " " + userRow.get(0).get(Access_User._family) + "</td>");
                html5.append("<td class='r' id='td" + i + "' onclick='hmisCommettes.addMembers(" + i + ")'><i id='" + roleRow.get(i).get(Role._id) + "' class='icon ion-plus-circled'  style='color:#676161;' ></i></td>");
                html5.append("</tr>");
            }
            html5.append("</tbody></table>");
            script += Js.setHtml("#tableRolesDiv", html5);
            /////////////////////////////////////////////////////

            Server.outPrinter(request, response, script + html2);
            return "";
        } catch (Exception ex) {
            Server.outPrinter(request, response, Server.ErrorHandler(ex));
            return "";
        }
    }

    /**
     * creator shohre shiran 13971211
     *
     * @param request
     * @param db
     * @param isPost
     * @return
     * @throws Exception
     */
    public static String insert(HttpServletRequest request, HttpServletResponse response, jjDatabaseWeb db, boolean needString) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_ins);
            if (!hasAccess.equals("")) {
                Server.outPrinter(request, response, hasAccess);
                return "";
            }
            StringBuilder html = new StringBuilder();
            jjCalendar_IR ir = new jjCalendar_IR();
            Map<String, Object> map = new HashMap<String, Object>();
            System.out.println("commettes_creatorId=" + jjTools.getSessionAttribute(request, "#ID"));
            map.put(_creatorId, jjTools.getSeassionUserId(request));
            map.put(_dateOfHoldingMeeting, jjTools.getParameter(request, _dateOfHoldingMeeting));
            map.put(_description, jjTools.getParameter(request, _description));
            map.put(_members, jjTools.getParameter(request, _members));
            map.put(_secretary, jjTools.getParameter(request, _secretary));
            map.put(_superwizar, jjTools.getParameter(request, _superwizar));
            map.put(_title, jjTools.getParameter(request, _title));
            map.put(_regulationFile, jjTools.getParameter(request, _regulationFile));
            map.put(_documnetsFile, jjTools.getParameter(request, _documnetsFile));
            map.put(_isActive, jjTools.getParameter(request, _isActive));

            DefaultTableModel dtm = db.insert(tableName, map);
            if (dtm.getRowCount() == 0) {

                String errorMessage = "عملیات درج به درستی صورت نگرفت.";
                if (jjTools.isLangEn(request)) {
                    errorMessage = "Edit Fail;";
                }
                Server.outPrinter(request, response, Js.modal(errorMessage, "پیام سامانه"));
                return "";
            }

            Server.outPrinter(request, response, Js.jjCommettes.refresh());
            return "";
        } catch (Exception ex) {
            Server.outPrinter(request, response, Server.ErrorHandler(ex));
            return "";
        }
    }

    /**
     * creator shohre shiran 13971211
     *
     * @param request
     * @param db
     * @param isPost
     * @return
     * @throws Exception
     */
    public static String add_new(HttpServletRequest request, HttpServletResponse response, jjDatabaseWeb db, boolean needString) throws Exception {
        try {
            StringBuilder html = new StringBuilder();

            boolean accIns = Access_User.hasAccess(request, db, rul_ins);
            if (accIns) {
                html.append(Js.setHtml("#Commette_button", "<button  id=\"insert_Commette_new\"  class=\"btn btn-outline-success active btn-block mg-b-10\" onclick='" + Js.jjCommettes.insert() + "'>" + lbl_insert + "</button>"));
            }
            String script = html.toString();

            Server.outPrinter(request, response, script);
            return "";
        } catch (Exception ex) {
            Server.outPrinter(request, response, Server.ErrorHandler(ex));
            return "";
        }
    }

    /**
     * creator shohre shiran 13971211
     *
     * @param request
     * @param db
     * @param isPost
     * @return
     * @throws Exception
     */
    public static String select(HttpServletRequest request, HttpServletResponse response, jjDatabaseWeb db, boolean needString) throws Exception {
        try {
            String id = jjTools.getParameter(request, _id);

            List<Map<String, Object>> row = jjDatabase.separateRow(db.Select(tableName, _id + "=" + id));
            if (row.size() == 0) {
                String errorMessage = "رکوردی با این کد وجود ندارد.";
                if (jjTools.isLangEn(request)) {
                    errorMessage = "Select Fail;";
                }
                Server.outPrinter(request, response, Js.modal(errorMessage, "پیام سامانه"));
                return "";
            }
            StringBuilder html = new StringBuilder();
            StringBuilder html2 = new StringBuilder();
            StringBuilder html3 = new StringBuilder();
            StringBuilder html4 = new StringBuilder();

            html.append(Js.setVal("#" + tableName + "_" + _id, row.get(0).get(_id)));
            html.append(Js.setVal("#" + _title, row.get(0).get(_title)));
            html.append(Js.setVal("#" + _documnetsFile, row.get(0).get(_documnetsFile)));
            html.append(Js.setVal("#" + _regulationFile, row.get(0).get(_regulationFile)));
            html.append(Js.setVal("#" + _dateOfHoldingMeeting, row.get(0).get(_dateOfHoldingMeeting)));
            html.append(Js.setVal("#" + _description, row.get(0).get(_description)));
            html.append(Js.setVal("#" + _members, row.get(0).get(_members)));
            html.append(Js.setVal("#" + _secretary, row.get(0).get(_secretary)));
            html.append(Js.setVal("#" + _superwizar, row.get(0).get(_superwizar)));
            html.append(Js.setVal("#" + _regulationFile, row.get(0).get(_regulationFile)));
            html.append(Js.setVal("#active", "1"));
            html.append(Js.setVal("#noActive", "0"));
            if (row.get(0).get(_isActive).equals("1")) {
                html.append(Js.setAttr("#active", "checked", "checked"));
            } else {
                html.append(Js.setAttr("#noActive", "checked", "checked"));
            }
            if (!row.get(0).get(_documnetsFile).toString().equals("")) {
                String[] documentFile = (row.get(0).get(_documnetsFile).toString().replaceAll("#A#", "%23A%23")).split("%23A%23");
                for (int i = 0; i < documentFile.length; i++) {
                    html3.append("<div class='col-lg-3'>");
                    html3.append("<a id='downloadFiledocument_commettes' title='دانلود فایل'  href='upload/" + documentFile[i] + "' class='btn btn-outline-success  btn-block mg-b-10'><input value='" + documentFile[i] + "' class='form-control is-valid hasDatepicker' /></a>");
                    html3.append("</div>");
                }
            }
            if (!row.get(0).get(_regulationFile).toString().equals("")) {
                String[] regulationFile = (row.get(0).get(_regulationFile).toString().replaceAll("#A#", "%23A%23")).split("%23A%23");
                for (int i = 0; i < regulationFile.length; i++) {
                    html4.append("<div class='col-lg-3'>");
                    html4.append("<a id='downloadRegulationFile_commettes' title='دانلود فایل'  href='upload/" + regulationFile[i] + "' class='btn btn-outline-success  btn-block mg-b-10'><input value='" + regulationFile[i] + "' class='form-control is-valid hasDatepicker' /></a>");
                    html4.append("</div>");
                }
            }
            html.append(Js.setAttr("#PicPreviewAttach", "src", "upload/" + row.get(0).get(_regulationFile) + ""));
            boolean accEdt = Access_User.hasAccess(request, db, rul_edt);//
            boolean accDel = Access_User.hasAccess(request, db, rul_dlt);//
            html2.append("<div class='row'>");
            if (accEdt) {
                html2.append("<div class=\"mg-t-20  col-lg\">");
                html2.append("<button  id='edit_Commettes'  class='btn btn-outline-warning btn-block mg-b-10' onclick='" + Js.jjCommettes.edit() + "'>" + lbl_edit + "</button>");
                html2.append("</div>");
            }
            if (accDel) {
                html2.append("<div class=\"mg-t-20  col-lg\">");
                html2.append("<button id='delete_Commettes' class='btn btn-outline-danger btn-block mg-b-10' onclick='" + Js.jjCommettes.delete(id) + "' >" + lbl_delete + " </button>");
                html2.append("</div>");
            }

            html2.append("</div>");
            String script = Js.setHtml("#Commette_button", html2);
            if (row.get(0).get(_members).toString().equals("")) {

            } else {
                String membersId = row.get(0).get(_members).toString();
                String[] memberId = (membersId.replaceAll("#A#", "%23A%23")).split("%23A%23");
                for (int i = 0; i < memberId.length; i++) {
                    script += ("$('#tableRolesDiv #refreshRoles #" + memberId[i] + "').attr('class','icon ion-checkmark-circled').css('color','green');");

                }
            }

            script += html.toString();
            script += Js.setHtml("#inputTextSelectorDiv1", html3);
            script += Js.setHtml("#inputTextSelectorDiv2", html4);
            Server.outPrinter(request, response, script);
            return "";
        } catch (Exception ex) {
            Server.outPrinter(request, response, Server.ErrorHandler(ex));
            return "";
        }
    }

    /**
     * creator shohre shiran 13971211
     *
     * @param request
     * @param db
     * @param isPost
     * @return
     * @throws Exception
     */
    public static String edit(HttpServletRequest request, HttpServletResponse response, jjDatabaseWeb db, boolean needString) throws Exception {
        try {
            String id = jjTools.getParameter(request, _id);
//            String errorMessageId = jjValidation.isDigitMessageFa(id, "کد");
//            if (!errorMessageId.equals("")) {
//                if (jjTools.isLangEn(request)) {
//                    errorMessageId = jjValidation.isDigitMessageEn(id, "ID");
//                }
//                Server.outPrinter(request, response, Js.modal(errorMessage,"پیام سامانه"));
//            }
            String hasAccess = Access_User.getAccessDialog(request, db, rul_edt);
            if (!hasAccess.equals("")) {
                Server.outPrinter(request, response, hasAccess);
                return "";
            }
            List<Map<String, Object>> Row = jjDatabase.separateRow(db.Select(tableName, _id + "=" + id));

            Map<String, Object> map = new HashMap<String, Object>();
            map.put(_dateOfHoldingMeeting, jjTools.getParameter(request, _dateOfHoldingMeeting));
            map.put(_description, jjTools.getParameter(request, _description));
            map.put(_members, jjTools.getParameter(request, _members));
            map.put(_secretary, jjTools.getParameter(request, _secretary));
            map.put(_superwizar, jjTools.getParameter(request, _superwizar));
            map.put(_title, jjTools.getParameter(request, _title));
            map.put(_regulationFile, jjTools.getParameter(request, _regulationFile));
            map.put(_isActive, jjTools.getParameter(request, _isActive));
            map.put(_documnetsFile, jjTools.getParameter(request, _documnetsFile));
            if (!db.update(tableName, map, _id + "=" + id)) {
                String errorMessage = "عملیات ویرایش به درستی صورت نگرفت.";
                if (jjTools.isLangEn(request)) {
                    errorMessage = "Edit Fail;";
                }
                Server.outPrinter(request, response, Js.modal(errorMessage, "پیام سامانه"));
                return "";
            }
            Server.outPrinter(request, response, Js.jjCommettes.refresh());
            return "";
        } catch (Exception ex) {
            Server.outPrinter(request, response, Server.ErrorHandler(ex));
            return "";
        }
    }

    public static String delete(HttpServletRequest request, HttpServletResponse response, jjDatabaseWeb db, boolean needString) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_dlt);
            if (!hasAccess.equals("")) {
                Server.outPrinter(request, response, hasAccess);
                return "";
            }
            String id = jjTools.getParameter(request, _id);
            System.out.println("id=" + id);

            if (!db.delete(tableName, _id + "=" + id)) {
                String errorMessage = "عملیات حذف به درستی صورت نگرفت";
                if (jjTools.isLangEn(request)) {
                    errorMessage = "Delete Fail;";
                }
                Server.outPrinter(request, response, Js.modal(errorMessage, "پیام سامانه"));
                return "";
            }
            Server.outPrinter(request, response, Js.jjCommettes.refresh());
            return "";
        } catch (Exception ex) {
            Server.outPrinter(request, response, Server.ErrorHandler(ex));
            return "";
        }
    }

    /**
     * نمایش جدول نقشها برای انتخاب مدعوین 13980120
     *
     * @param request
     * @param db
     * @param isPost
     * @return
     * @throws Exception
     */
    public static String showInvitationForm(HttpServletRequest request, HttpServletResponse response, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {

            StringBuilder html = new StringBuilder();
            StringBuilder html2 = new StringBuilder();
            StringBuilder html3 = new StringBuilder();
            String commettesId = jjTools.getParameter(request, _id);
            System.out.println("commettesId=" + commettesId);
            List<Map<String, Object>> commettesRow = jjDatabase.separateRow(db.Select(Commettes.tableName, Commettes._id + "=" + commettesId));
            List<Map<String, Object>> sessionsRow = jjDatabase.separateRow(db.Select(Sessions.tableName, Sessions._id + "=" + commettesId));
            html.append("<label class='ckbox'>");
            String memberId = commettesRow.get(0).get(Commettes._members).toString();
            String[] membersId = (memberId.replaceAll("#A#", "%23A%23")).split("%23A%23");
            for (int i = 0; i < membersId.length; i++) {
                List<Map<String, Object>> roleRow = jjDatabase.separateRow(db.Select(Role.tableName, Role._id + "=" + membersId[i]));
                List<Map<String, Object>> userRow = jjDatabase.separateRow(db.Select(Access_User.tableName, Access_User._id + "=" + roleRow.get(0).get(Role._user_id)));
                html.append("<div class=\"form-group has-danger mg-b-0\">");
                html.append("<label class=\"ckbox\">");
                html.append("<input type = \"checkbox\" value ='" + roleRow.get(0).get(Role._id) + "' class=\"checkBoxInvitees\" name = \"sessions_Invitees\" id = \"sessions_Invitees" + roleRow.get(0).get(Role._user_id) + "\" > ");
                html.append("<span>" + userRow.get(0).get(Access_User._name) + " " + userRow.get(0).get(Access_User._family) + "-" + roleRow.get(0).get(Role._title) + "");
                html.append("</span>");
                html.append("</label>");
                html.append("</div>");
            }
            /////////////////////////////////////////////////////////////////////////////////////////////
            List<Map<String, Object>> usersRow = jjDatabase.separateRow(db.Select(Access_User.tableName));

            html2.append("<option value=''>انتخاب</option>");
            for (int i = 0; i < usersRow.size(); i++) {
                html2.append("<option value='" + usersRow.get(i).get(Access_User._id) + "'>" + usersRow.get(i).get(Access_User._name) + " " + usersRow.get(i).get(Access_User._family) + "</option> ");
            }

            String script = "";
            script += Js.setHtml("#sessions_InviteesInSide", html2);
            script += Js.setHtml("#invitessDiv", html);
            Server.outPrinter(request, response, script);
            return "";
        } catch (Exception ex) {
            Server.outPrinter(request, response, Server.ErrorHandler(ex));
            return "";
        }
    }

    /**
     * ارسال پیام برای مدعوین داخل و خارج از سازمان و کاربرانی که در سیستم ثبت
     * نام نکرده اند در اکسس یوزر ثبت می شوند
     *
     * @param request
     * @param response
     * @param db
     * @param isPost
     * @return
     * @throws Exception
     */
    public static String sendComment(HttpServletRequest request, HttpServletResponse response, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            StringBuilder html = new StringBuilder();
            String Email = "";
            String nameAndFamily = "";
            String phone = "";
            String role = "";

            String script = "";
            String textComment = jjTools.getParameter(request, "textComment");//متن پیام
            String inviteesIdComment = jjTools.getParameter(request, "inviteesIdComment");//ای دی مدعوین 
            String inviteesOutSideIdComment = jjTools.getParameter(request, "inviteesOutSideIdComment");//ای دی مدعوین مهمان
            String inviteesInSideIdComment = jjTools.getParameter(request, "inviteesInSideIdComment");//ای دی مدعوین داخل سازمان
            String[] inviteesOutSideId = (inviteesInSideIdComment.replaceAll("#A#", "%23A%23")).split("%23A%23");

            for (int i = 0; i < inviteesOutSideId.length; i++) {
                String[] userInformation = inviteesOutSideId[i].split(",");
                nameAndFamily = userInformation[0];
                phone = userInformation[1];
                Email = userInformation[2];
                role = userInformation[3];
Server.sendEmail("", Email, "دعوتنامه"+textComment+ "", "", isPost, request);
            }
            
            Server.outPrinter(request, response, script);
            return "";
        } catch (Exception ex) {
            Server.outPrinter(request, response, Server.ErrorHandler(ex));
            return "";
        }
    }
}
