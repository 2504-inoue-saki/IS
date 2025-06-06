$(document).ready(function() {

    // 削除ボタン押下時の処理（投稿、コメントのどちらにも適用）
	$('.delete').on('click', function() {
		if (confirm("削除します。よろしいですか?")) {
			return true;
		}
		return false;
	});

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
});