QUnit.config.autostart = false;

(function(t, $) {
	var tests = [];
	
	t.done(function(result) {
		$('#qunit').addClass('finished');
		if (result.failed > 0) {
			$('#qunit').addClass('failed');
		}
		$('#qunit').addClass('success');
	});

	function querystring(query, l) {
		var Params = function() {

		}
		Params.prototype.get = function(name) {
			var ret = this[name];
			if (ret && ret.length) {
				return ret[0];
			}
			return null;
		}

		var re = /([^&=]+)=?([^&]*)/g;
		function decode(str) {
			return decodeURIComponent(str.replace(/\+/g, ' '));
		}

		if (!query) {
			var url = ('' + (l || location));
			var idx = url.indexOf('?');
			if (idx < 0) {
				return new Params();
			}
			return querystring(url.substring(idx));
		}
		var params = new Params();
		var e;
		if (query) {
			if (query.substr(0, 1) == '?') {
				query = query.substr(1);
			}

			while (e = re.exec(query)) {
				var k = decode(e[1]);
				var v = decode(e[2]);
				params[k] = params[k] || [];
				params[k].push($.trim(v));
			}
		}
		return params;
	}

	var testeSelecionado = querystring().get('t');
	console.info('testeSelecionado', testeSelecionado);
	if (testeSelecionado) {
		tests.push('' + testeSelecionado);
	} else {
		tests.push('test/page/test-login.js');
	}

	for ( var idx in tests) {
		var test = tests[idx];
		$.getScript(test, function() {
			var idxoff = tests.indexOf(test);
			tests.splice(idxoff, 1);
			if (tests.length == 0) {
				t.start();
			}
		});
	}

})(QUnit, jQuery);