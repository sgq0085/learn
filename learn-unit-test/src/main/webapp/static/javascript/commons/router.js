function router(page, param, isBlank) {
    var url = parent.location.pathname;
    var form = document.createElement("form");
    var frag = document.createDocumentFragment();
    frag.appendChild(form);

    form.method = 'post';
    form.action = url;
    if (isBlank) {
        form.target = '_blank';
    }
    // 创建隐藏表单
    var element = document.createElement("input");
    element.setAttribute("type", "hidden");
    element.setAttribute("name", "forward_by_router");
    element.setAttribute("value", page);
    form.appendChild(element);
    if (param != null) {
        var key = [];
        for (var i in param) {
            key.push(i);
        }
        for (var i = 0; i < key.length; i++) {
            element = document.createElement("input");
            element.setAttribute("type", "hidden");
            element.setAttribute("name", key[i]);
            element.setAttribute("value", param[key[i]]);
            form.appendChild(element);
        }
    }
    document.body.appendChild(frag);
    form.submit();
}
