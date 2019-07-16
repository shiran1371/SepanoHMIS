/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var hmisMessenger = {
    tableName: "Messenger",
    f_id: "id",
    loadForm: function () {
        if ($("#swMessengerForm").html() == '') {
            $("#swMessengerForm").load("formHMIS/messenger.html", null, function () {
                new jj('#messenger_postageDate').jjCalendarWithYearSelector(1310, 1410);
                $("#cancel_Messenger").on('click', function (e) {
//                   alert(1);
                    hmisMessenger.m_clean();
                    hmisMessenger.m_show_tbl();
                });


                new jj('#send').jjAjaxFileUploadTitleUploadFiles('#attachFile', '#messenger_attachFile', 'messenger_titleFile', '#messenger_attachFileTitle');


                hmisMessenger.selectOptionUser("messenger_receiver");
                hmisMessenger.sendMetod1();

            });


        }
    },
    selectOptionUser: function (panel) {
        var param = "";
        param += "panel=" + panel;
        param += "&do=" + hmisMessenger.tableName + ".selectOptionUser";

        new jj(param).jjAjax2(false);
    },
    alert: function () {
        new jj("امکان حذف برای شما وجود ندارد").jjModal("پیام سامانه");
    },
    sendMesseageToSignatory: function (userId, IdDocumentary) {
        var param = "";
        param += "&userId=" + userId;
        param += "&IdDocumentary=" + IdDocumentary;

        param += "&titleSign=" + $("#payam").parent().parent().find(".c").val();
        param += "&do=" + hmisMessenger.tableName + ".sendMesseageToSignatory";
        param += "&jj=1";
        new jj(param).jjAjax2(false);

    },
    m_refresh: function (containerId, sortField, tableHeight) {
        var param = "";
        param += "do=" + hmisMessenger.tableName + ".refresh";
        param += "&panel=" + (containerId == null ? "swMessengerTbl" : containerId);
        param += "&sort=" + (sortField == null ? "0" : sortField);
        param += "&height=" + (tableHeight == null ? PanelHeight : tableHeight);
        param += "&jj=1";
        new jj(param).jjAjax2(false);
        hmisMessenger.tabSizeTbl();
    },
    m_refreshMyMessages: function (containerId, sortField, tableHeight) {
        var param = "";
        param += "do=" + hmisMessenger.tableName + ".refreshMyMessages";
        param += "&panel=" + (containerId == null ? "swMyMessagesTbl" : containerId);
        param += "&sort=" + (sortField == null ? "0" : sortField);
        param += "&height=" + (tableHeight == null ? PanelHeight : tableHeight);
        param += "&jj=1";
        new jj(param).jjAjax2(false);
        hmisMessenger.tabSizeTbl();
    },
    bazgasht: function () {
        hmisMessenger.m_clean();
        hmisMessenger.m_show_tbl();
    },
    m_add_new: function () {
        new jj("do=" + hmisMessenger.tableName + ".add_new").jjAjax2(false);
        $('#messenger_receiver').val("null").trigger('change');
         $("#messenger_attachFileTitle").val("");
        $("#messenger_attachFile").val("");
        $('#status').hide();
        $('#logStatus').hide();
        $(".inputAfterSelectManager").html("");
        $(".inputSelectorDiv").html("");
        hmisMessenger.m_show_form();
        hmisMessenger.m_clean();



        //
//        part_content_editor = new jj('#department_publicContent').jjEditor();
//        part_praivate_editor = new jj('#department_praivateContent').jjEditor();
//        cmsUser.m_getGroups();
    },
    m_show_form: function () {
        $('#swMessengerTbl').hide();
        $('#swMessengerForm').show();
        if ($('#swMessengerTbl').html() == "") {
            hmisMessenger.m_refresh();
        }
        hmisMessenger.tabSizeForm();
    },
    m_clean: function () {
        new jj("#swMessengerForm").jjFormClean();
        $("#messenger_receiver").val('').trigger('change');

    },
    m_show_tbl: function () {
        $('#swMessengerTbl').show();
        $('#swMessengerForm').hide();
        if ($('#swMessengerTbl').html() == "") {
            hmisMessenger.m_refresh();
        }
        hmisMessenger.m_refresh();
        hmisMessenger.tabSizeTbl();
    },
    m_insert: function () {

        var param = "";
        param += "do=" + hmisMessenger.tableName + ".insert";
        param += "&" + new jj("#swMessengerForm").jjSerial();

        param += "&jj=1";
        param += "&messenger_receiver=" + $("#messenger_receiver option:selected").val();
        new jj(param).jjAjax2(false);

        hmisMessenger.m_show_tbl();
        hmisMessenger.m_clean();
    },
    ///
    //اضافه کردن روش ارسال htmlبه 
    ///
    
    sendMetod1: function () {

        var param = "";
        param += "do=" + hmisMessenger.tableName + ".sendMetod1";
       
        new jj(param).jjAjax2(false);

    },
    sendMetod2: function () {

        var param = "";
        param += "do=" + hmisMessenger.tableName + ".sendMetod2";
       
        new jj(param).jjAjax2(false);

    },
    m_edit: function () {
        var param = "";
        param += "&do=" + hmisMessenger.tableName + ".edit";

        param += "&" + new jj("#swMessengerForm").jjSerial();
        param += "&jj=1";
        param += "&messenger_receiver=" + $("#messenger_receiver option:selected").val();
        new jj(param).jjAjax2(false);
        $(".inputSelectorDiv").html('');
        

        $(".inputAfterSelectManager").html("");
        hmisMessenger.m_show_tbl();
        hmisMessenger.m_clean();
    },
    ////این تابع برای ویرایش پیام های خوانده نشده نوشته شده
    m_editUnreadMessages: function () {
        var param = "";
       
        param += "&do=" + hmisMessenger.tableName + ".edit";

        param += "&" + new jj("#swUnreadMessagesForm").jjSerial();
        param += "&jj=1";
        param += "&messenger_receiver=" + $("#unreadMessages_receiver option:selected").val();
        new jj(param).jjAjax2(false);
        $(".inputSelectorDiv").html('');
        

        $(".inputAfterSelectManager").html("");
        hmisUnreadMessages.m_show_tbl();
        hmisUnreadMessages.m_clean();
    },
    m_editMyMessages: function () {
        var param = "";
       
        param += "&do=" + hmisMessenger.tableName + ".edit";

        param += "&" + new jj("#swMyMessagesForm").jjSerial();
        param += "&jj=1";
        param += "&messenger_receiver=" + $("#MyMessages_receiver option:selected").val();
        new jj(param).jjAjax2(false);
        $(".inputSelectorDiv").html('');
        

        $(".inputAfterSelectManager").html("");
        hmisMyMessages.m_show_tbl();
        hmisMyMessages.m_clean();
    },
    /*
     * این تابع برای ویرایش پیام های دیده شده نوشته شده است
     */
    m_editMessagesSeen: function () {
        var param = "";
       
        param += "&do=" + hmisMessenger.tableName + ".edit";

        param += "&" + new jj("#swMessagesSeenForm").jjSerial();
        param += "&jj=1";
        param += "&messenger_receiver=" + $("#MessagesSeen_receiver option:selected").val();
        new jj(param).jjAjax2(false);
        $(".inputSelectorDiv").html('');
        

        $(".inputAfterSelectManager").html("");
        hmisMessagesSeen.m_show_tbl();
        hmisMessagesSeen.m_clean();
    },
    /*
     * این تابع برای ویرایش پیام ها نوشته شده است
     */
    m_editMessages: function () {
        var param = "";
       
        param += "&do=" + hmisMessenger.tableName + ".edit";

        param += "&" + new jj("#swMessagesForm").jjSerial();
        param += "&jj=1";
        param += "&messenger_receiver=" + $("#Messages_receiver option:selected").val();
        new jj(param).jjAjax2(false);
        $(".inputSelectorDiv").html('');
        

        $(".inputAfterSelectManager").html("");
        hmisMessages.m_show_tbl();
        hmisMessages.m_clean();
    },
       ////این تابع برای حذف پیام های خوانده نشده نوشته شده
     m_deleteUnread: function (id) {
        new jj("آیا از حذف این رکورد اطمینان دارید؟").jjModal_Yes_No("پیام هشدار قبل از حذف", "hmisUnreadMessages.m_delete_after_question(" + id + ");");
    },
       ////این تابع برای حذف پیام های من نوشته شده
     m_deleteMyMessages: function (id) {
        new jj("آیا از حذف این رکورد اطمینان دارید؟").jjModal_Yes_No("پیام هشدار قبل از حذف", "hmisMyMessages.m_delete_after_question(" + id + ");");
    },
    ////این تابع برای حذف پیام های دیده شده نوشته شده
     m_deleteMessagesSeen: function (id) {
        new jj("آیا از حذف این رکورد اطمینان دارید؟").jjModal_Yes_No("پیام هشدار قبل از حذف", "hmisMessagesSeen.m_delete_after_question(" + id + ");");
    },
     m_deleteMessages: function (id) {
        new jj("آیا از حذف این رکورد اطمینان دارید؟").jjModal_Yes_No("پیام هشدار قبل از حذف", "hmisMessages.m_delete_after_question(" + id + ");");
    },
  
    m_delete: function (id) {
        new jj("آیا از حذف این رکورد اطمینان دارید؟").jjModal_Yes_No("پیام هشدار قبل از حذف", "hmisMessenger.m_delete_after_question(" + id + ");");
    },
    m_delete_after_question: function (id) {
        var param = "";
        param += "do=" + hmisMessenger.tableName + ".delete";
        param += "&" + hmisMessenger.f_id + "=" + (id == null ? "" : id);
        new jj(param).jjAjax2(false);
        hmisMessenger.m_show_tbl();
        hmisMessenger.m_clean();
    },
    m_remove: function (idUpload, id) {
        new jj("آیا از حذف این رکورد اطمینان دارید؟").jjModal_Yes_No("پیام هشدار قبل از حذف", "hmisMessenger.removeFile(" + idUpload + "," + id + ");");
    },
    removeFile: function (idUpload, id) {

        var param = "";
        param += "do=" + hmisMessenger.tableName + ".removeFile";
        param += "&upload_id=" + idUpload;
        param += "&messenger_id=" + id;
        new jj(param).jjAjax2(false);
        hmisMessenger.m_show_tbl();
        hmisMessenger.m_clean();
    },
    m_select: function (id) {
        var param = "";
        param += "do=" + hmisMessenger.tableName + ".select";
        param += "&" + hmisMessenger.f_id + "=" + (id == null ? "" : id);
        $('#status').show();
        $('#logStatus').show();
        $(".inputSelectorDiv").html("");
        $(".inputAfterSelectManager").html("");
       
//        $('#UserSelectOption').trigger('change');

        new jj(param).jjAjax2(false);



        hmisMessenger.m_show_form();


    },
    m_getMenu: function () {
        var param = "";
        param += "do=" + hmisMessenger.tableName + ".getMenu";
        new jj(param).jjAjax2(false);
    },
    tabSizeTbl: function () {
        $('#swMessenger').css('height', 515);
        hmisMessenger.heightTab = 515;
    },
    tabSizeForm: function () {
        $('#swMessenger').css('height', 270);
        hmisMessenger.heightTab = 270;
    },
    heightTab: "515",
    mainTabSetSize: function () {
        $('#swMessenger').css('height', hmisMessenger.heightTab);
    },
}


