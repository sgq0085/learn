<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
    /* <![CDATA[ */
    /* 自定义Forms */
    .bs.form-inline select,
    .bs.form-inline input[type="text"],
    .bs.form-inline input[type="password"] {
        width: 180px;
    }

    .bs-control-sizing select,
    .bs-control-sizing input[type="text"] + input[type="text"] {
        margin-top: 10px;
    }

    .bs--form .input-group {
        margin-bottom: 10px;
    }

    .bs-navbar-fixed-top {
        position: fixed;
        z-index: 1030;
        border-width: 0 0 1px;
    }

    @media (min-width: 768px) {
        .bs-navbar-fixed-top {
            width: 720px;
        }
    }

    @media (min-width: 992px) {
        .bs-navbar-fixed-top {
            width: 940px;
        }
    }

    @media (min-width: 1200px) {
        .bs-navbar-fixed-top {
            width: 1140px;
        }
    }

    /*
    * Glyphicons
    *
    * Special styles for displaying the icons and their classes in the docs.
    */

    .bs-glyphicons {
        padding-left: 0;
        padding-bottom: 1px;
        margin-bottom: 20px;
        list-style: none;
        overflow: hidden;
    }

    .bs-glyphicons li {
        color: #428BCA;
        float: left;
        width: 110px;
        height: 110px;
        padding: 10px;
        /* margin: 0 -1px -1px 0;
        font-size: 12px;
        line-height: 1.4; */
        text-align: center;
    }

    .bs-glyphicons .glyphicon {
        margin-top: 5px;
        margin-bottom: 10px;
        font-size: 24px;
    }

    .bs-glyphicons .glyphicon-class {
        display: block;
        text-align: center;
        word-wrap: break-word; /* Help out IE10+ with class names */
    }

    .bs-glyphicons li:hover {
        background-color: rgba(86, 61, 124, .1);
    }

    .icon-edit {
        background-image: url("../images/edit-btn.png");
        background-repeat: no-repeat;
        cursor: pointer;
        display: inline-block;
        height: 16px;
        vertical-align: text-top;
        width: 16px;
    }

    .text-group-addon {
        line-height: 1;
        white-space: nowrap;
        vertical-align: middle;
        display: table-cell;
    }

    .form-horizontal .td-label {
        float: left;
        position: relative;
        min-height: 1px;
        padding-right: 0px;
        padding-left: 15px;
        padding-top: 5px;
        margin-top: 0;
        margin-bottom: 0;
        text-align: right;
    }

    .col-td-1, .col-td-2, .col-td-middle, .col-td-3, .col-td-4, .col-td-5, .col-td-6, .col-td-7, .col-td-8, .col-td-9, .col-td-10, .col-td-11, .col-td-12 {
        float: left;
        position: relative;
        min-height: 1px;
        padding-right: 5px;
        padding-left: 6px;
    }

    .col-td-12 {
        width: 100%;
    }

    .col-td-11 {
        width: 91.66666667%;
    }

    .col-td-10 {
        width: 83.33333333%;
    }

    .col-td-9 {
        width: 75%;
    }

    .col-td-8 {
        width: 66.66666667%;
    }

    .col-td-7 {
        width: 58.33333333%;
    }

    .col-td-6 {
        width: 50%;
    }

    .col-td-5 {
        width: 41.66666667%;
    }

    .col-td-4 {
        width: 33.33333333%;
    }

    .col-td-3 {
        width: 25%;
    }

    .col-td-middle {
        width: 20%;
    }

    .col-td-2 {
        width: 16.66666667%;
    }

    .col-td-1 {
        width: 8.33333333%;
    }

    /* 定制复杂表单使用dl-dt-dd形式 */
    .dl_form {
        border-left: 1px solid #ddd;
        border-bottom: 1px solid #ddd;
        margin-bottom: 5px;
    }

    .dl_form:before, .dl_form:after {
        display: table;
        content: " ";
    }

    .dl_form:after {
        clear: both;
    }

    .dl_form dl {
        border-right: 1px solid #ddd;
        margin-bottom: 0px;

    }

    .dl_form dt {
        border-top: 1px solid #ddd;
        border-bottom: 1px solid #ddd;
        padding: 5px 0 0 5px;
        height: 30px;
        background: #f5f5f5;
        font-weight: normal;
        font-size: 12px;
    }

    .dl_form dd {
        padding: 5px;
        height: 40px;
    }

    .col-10-1, .col-10-2, .col-10-3, .col-10-4, .col-10-5, .col-10-6, .col-10-7, .col-10-8, .col-10-9, .col-10-10 {
        float: left;
        position: relative;
        min-height: 1px;
        min-width: 160px;
    }

    .col-10-10 {
        width: 100%;
    }

    .col-10-9 {
        width: 90%;
    }

    .col-10-8 {
        width: 80%;
    }

    .col-10-7 {
        width: 70%;
    }

    .col-10-6 {
        width: 60%;
    }

    .col-10-5 {
        width: 50%;
    }

    .col-10-4 {
        width: 40%;
    }

    .col-10-3 {
        width: 30%;
    }

    .col-10-2 {
        width: 20%;
    }

    .col-10-1 {
        width: 10%;
    }

    .col-16-1, .col-16-2, .col-16-3, .col-16-4, .col-16-5, .col-16-6, .col-16-7, .col-16-8, .col-16-9, .col-16-10, .col-16-11, .col-16-12, .col-16-13, .col-16-14, .col-16-15, .col-16-16 {
        float: left;
        position: relative;
        min-height: 1px;
        /* min-width:160px; */
    }

    .col-16-16 {
        width: 100%;
    }

    .col-16-15 {
        width: 93.75%;
    }

    .col-16-14 {
        width: 87.5%;
    }

    .col-16-13 {
        width: 81.25%;
    }

    .col-16-12 {
        width: 75%;
    }

    .col-16-11 {
        width: 68.75%;
    }

    .col-16-10 {
        width: 62.5%;
    }

    .col-16-9 {
        width: 56.25%;
    }

    .col-16-8 {
        width: 50%;
    }

    .col-16-7 {
        width: 43.75%;
    }

    .col-16-6 {
        width: 37.5%;
    }

    .col-16-5 {
        width: 31.25%;
    }

    .col-16-4 {
        width: 25%;
    }

    .col-16-3 {
        width: 18.75%;
    }

    .col-16-2 {
        width: 12.5%;
    }

    .col-16-1 {
        width: 6.25%;
    }

    /* ]]> */
</style>