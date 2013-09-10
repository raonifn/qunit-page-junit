(function(t, $) {
	t.module("Login");

	t.pageTest('Login Test', function(page) {

		page.open('index.html');

		page.step('Logar', [ '#btn-login' ], function(btlogin) {
			var form = btlogin.parent('form');
			form.find('input[name="username"]').val('mock');
			form.find('input[name="password"]').val('mock');
			page.click(btlogin);
		});

		page.step('Verificar Logado', [ '#logout', 'div.dadosLogin span' ],
				function(logout, dadosLogin) {
					t.equal('Mock User', dadosLogin.text());
				});

		page.step('Logout', [ '#logout' ], function(logout) {
			page.click(logout);
		});

	});
})(QUnit, jQuery);