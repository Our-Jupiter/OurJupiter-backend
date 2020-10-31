var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var form = new FormData($("#UploadForm")[0]);

        form.append('title',$('#title').val());
        form.append('content',$('#content').val());
        form.append('author',$('#author').val());

        for (let value of form.values()) {
            console.log(value);
        }

        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: '/api/v1/posts',
            processData: false,
            contentType: false,
            data: form
        }).done(function(data) {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {

        var form = new FormData($("#FixForm")[0]);

        form.append('title',$('#title').val());
        form.append('content',$('#content').val());

        for (let value of form.values()) {
            console.log(value);
        }

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            enctype: 'multipart/form-data',
            url: '/api/v1/posts/'+id,
            processData: false,
            contentType: false,
            data: form
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();
        console.log(id);

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();