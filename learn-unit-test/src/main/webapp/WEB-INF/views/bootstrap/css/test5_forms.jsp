<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../layouts/default.jsp" %>
<head>
    <title>表单</title>
</head>
<body>


<div class="container">
    <h3>基本案例</h3>

    <p>单独的表单控件会被自动赋予一些全局样式。所有设置了<code>.form-control</code>的<code> input </code>、
        <code> textarea </code>和<code> select </code>元素都将被默认设置为width:100%;。<br>
        将label和前面提到的这些控件包裹在<code>.form-group</code>中可以获得最好的排列。
    </p>

    <form role="form">
        <div class="form-group">
            <label for="exampleInputEmail1" class="control-label">Email address</label>
            <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1" class="control-label">Password</label>
            <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
        </div>
        <div class="form-group">
            <label for="exampleInputFile">File input</label>
            <input type="file" id="exampleInputFile">

            <p class="help-block">Example block-level help text here.</p>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox"/> Check me out
            </label>
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
    </form>
</div>
<hr>
<div class="container">
    <h3>内联表单</h3>

    <p>为<code> form </code>设置元素<code>.form-inline</code>可使其内容左对齐并且表现为inline-block级别的控件。</p>

    <form class="form-inline" role="form">
        <div class="form-group">
            <label for="exampleInputEmail2" class="control-label">Email address: </label>
            <input type="email" class="form-control" id="exampleInputEmail2" placeholder="Enter email">
        </div>
        <div class="form-group">
            <label class="sr-only" for="exampleInputPassword2">Password</label>
            <input type="password" class="form-control" id="exampleInputPassword2" placeholder="Password">
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox"> Remember me
            </label>
        </div>
        <button type="submit" class="btn btn-default">Sign in</button>
    </form>
</div>
<hr>
<div class="container">
    <h3>水平排列的表单</h3>

    <p>
        通过为表单添加<code>.form-horizontal</code>，并使用Bootstrap预置的栅格class可以将label和控件组水平并排布局。<br>
        这样做将改变<code>.form-group</code>的行为，使其表现为栅格系统中的行（row），因此就无需再使用<code>.row</code>了。<br>
        注意: <code> label </code>需要配合样式<code>.control-label</code>，右对齐
    </p>

    <form class="form-horizontal">
        <div class="form-group">
            <label for="inputEmail3" class="col-md-2 control-label">Email</label>

            <div class="col-md-10">
                <input type="email" id="inputEmail3" class="form-control" placeholder="Email">
            </div>
        </div>
        <div class="form-group">
            <label for="exampleInputPassword3" class="col-md-2 control-label">Password</label>

            <div class="col-md-10">
                <input type="password" id="exampleInputPassword3" class="form-control" placeholder="password"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-10 col-md-offset-2">
                <div class="checkbox">
                    <label>
                        <input type="checkbox"> Remember me
                    </label>
                </div>
            </div>
        </div>


    </form>
</div>

<hr>

<h2>被支持的控件</h2>

<div class="container">
    <form class="form-horizontal">
        <h3>Input</h3>

        <div class="form-group">
            <label class="col-md-2 control-label" for="test1"><code>input</code>必须正确设置<code>type</code>属性</label>

            <div class="col-md-10">
                <input id="test1" type="text" class="form-control" placeholder="Test Input">
            </div>
        </div>

        <hr>

        <h3>Textarea</h3>

        <div class="form-group">
            <div class="row">
                <label for="testArea1" class="col-md-2 control-label">通过<code>rows</code>属性控制行数</label>

                <div class="col-md-10">
                    <textarea class="form-control" id="testArea1" rows="3"></textarea>
                </div>
            </div>
        </div>

        <h3>Checkbox 和 radio</h3>

        <p>普通的<code>.checkbox</code> 和 <code>.radio</code></p>

        <div class="checkbox">
            <label>
                <input type="checkbox" value="1">
                复选框
            </label>
        </div>

        <div class="radio">
            <label>
                <input type="radio" name="optionsRadios1" id="optionsRadios1" value="option1" checked>
                单选框&mdash;A选项
            </label>
        </div>
        <div class="radio">
            <label>
                <input type="radio" name="optionsRadios1" id="optionsRadios2" value="option2">
                单选框&mdash;B选项
            </label>
        </div>
        <p><code>.checkbox-inline</code> 或 <code>.radio-inline</code> 使这些控件排列在一行</p>
        <label class="checkbox-inline">
            <input type="checkbox" id="inlineCheckbox1" value="option1"> 1
        </label>
        <label class="checkbox-inline">
            <input type="checkbox" id="inlineCheckbox2" value="option2"> 2
        </label>
        <label class="checkbox-inline">
            <input type="checkbox" id="inlineCheckbox3" value="option3"> 3
        </label>

        <label class="radio-inline">
            <input type="radio" name="optionsRadios2" id="optionsRadios3" value="option3" checked>
            单选框&mdash;A选项
        </label>

        <label class="radio-inline">
            <input type="radio" name="optionsRadios2" id="optionsRadios4" value="option4">
            单选框&mdash;B选项
        </label>


        <h3>Select</h3>
        使用默认选项或添加<code>multiple</code>属性可以显示多个选项。
        <select class="form-control">
            <option>1</option>
            <option>2</option>
            <option>3</option>
            <option>4</option>
            <option>5</option>
        </select>
        <br>
        <select multiple class="form-control">
            <option>1</option>
            <option>2</option>
            <option>3</option>
            <option>4</option>
            <option>5</option>
        </select>

        <h3>静态控件</h3>

        <p>为<code>p</code>元素添加<code>.form-control-static</code></p>

        <div class="form-group">
            <label class="col-md-2 control-label">label:</label>

            <div class="col-md-10"><p class="form-control-static">class 设置为 form-control-static 的文本</p></div>
        </div>

        <h3>控件状态</h3>

        <p>1.输入焦点 设置了<code>.form-control</code>的<code> input </code> 会被设置输入焦点样式</p>

        <p>2.<code>disabled</code>属性,防止用户输入</p>
        <input class="form-control" id="disabledInput" type="text" placeholder="Disabled input here..." disabled>

        <h3>校验状态</h3>

        <p><code>.has-warning</code>、<code>.has-error</code>或<code>.has-success</code>到这些控件的父元素即可。<br>
            任何包含在此元素之内的<code>.control-label</code>、<code>.form-control</code>和<code>.help-block</code>都将接受这些校验状态的样式</p>

        <div class="form-group has-success">
            <label class="control-label" for="inputSuccess">Input with success</label>
            <input type="text" class="form-control" id="inputSuccess">
        </div>
        <div class="form-group has-warning">
            <label class="control-label" for="inputWarning">Input with warning</label>
            <input type="text" class="form-control" id="inputWarning">
        </div>
        <div class="form-group has-error">
            <label class="control-label">Input with error
                <input type="text" class="form-control" id="inputError">
            </label>
        </div>


        <h3>控件尺寸</h3>

        <p>高度尺寸</p>

        <input class="form-control input-lg" type="text" placeholder=".input-lg">
        <input class="form-control" type="text" placeholder="Default input">
        <input class="form-control input-sm" type="text" placeholder=".input-sm">

        <select class="form-control input-lg">...</select>
        <select class="form-control">...</select>
        <select class="form-control input-sm">...</select>

        <p>用栅格系统中的列包裹input或其任何父元素，都可很容易的为其设置宽度。</p>


        <div class="row">
            <div class="col-xs-2">
                <input type="text" class="form-control" placeholder=".col-xs-2">
            </div>
            <div class="col-xs-3">
                <input type="text" class="form-control" placeholder=".col-xs-3">
            </div>
            <div class="col-xs-4">
                <input type="text" class="form-control" placeholder=".col-xs-4">
            </div>
        </div>

        <h3>帮助文本</h3>

        <p>用于表单控件的块级帮助文本。</p>

        <span class="help-block">自己独占一行或多行的块级帮助文本。</span>

    </form>
</div>
</body>
</html>
