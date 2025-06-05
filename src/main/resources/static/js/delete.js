$(function() {
	$('.delete').on('click', function() {
		if (confirm($(this).parents('.delete-button').find('input[name="deleteContent"]').val()+ "：\n" + $(this).parents('.delete-button').find('input[name="deleteInfo"]').val() + "\n削除します。よろしいですか?")) {
			return true;
		}
		return false;
	});
});