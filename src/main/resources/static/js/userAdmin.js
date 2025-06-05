$(document).ready(function() {
    // ユーザー停止状態プルダウン変更時にボタンを活性化するロジック
    $('.select-box').on('change', function() {
        var $form = $(this).closest('form');
        var $button = $form.find('.update-isStopped-button');
        $button.prop('disabled', false);
    });

    // 「変更」ボタンクリック時に確認ダイアログを表示するロジック
    $('.update-isStopped-button').on('click', function() {
       if (confirm($(this).parents('.update-isStopped').find('input[name="userName"]').val() + "を" + $(this).parents('.update-isStopped').find('option:selected').text() + "状態にします。よろしいですか？")) {
          return true; // OKならフォームを送信
       }
       return false; // キャンセルならフォーム送信を中止
    });

    // ラジオボタンが変更されたときの処理
    $('.user-radio').on('change', function() {
        var selectedUserId = $(this).val(); // 選択されたラジオボタンのユーザーIDを取得
        var $editButton = $('.edit-selected-user-button'); // 「選択したユーザーを編集」ボタンを取得

        if (selectedUserId) { // 何らかのラジオボタンが選択されている場合
            $editButton.prop('disabled', false); // ボタンを活性化
        }
    });

    // 「ユーザ編集」ボタンがクリックされたときの処理
    $('.edit-selected-user-button').on('click', function() {
        var selectedUserId = $('input[name="selectedUserId"]:checked').val(); // 選択されているラジオボタンの値を取得

        if (selectedUserId) {
            // 選択されたユーザーの編集画面へリダイレクト
            window.location.href = '/user/edit/' + selectedUserId;
        }
    });
});