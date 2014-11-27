/**
 * @author yaroslav.yermilov
 */

String text = '''
Поиск слова, введенного пользователем
Поиск и определение частоты встречаемости осуществляется в текстовом файле, расположенном на сервере
'''

String searchToken = request.getParameter('searchToken')

String result = ''
if (searchToken) result = text.indexOf(searchToken)

html.html {
    head {
        title 'Lab 06'
    }
    body {
        h4 'Text'

        p text

        form (method: 'post', action: 'Lab06.groovy') {
            p 'Input'
            input (type: 'text', name: 'searchToken')
            input (type: 'submit', value: 'Submit')
        }

        p "Position: ${result}"
    }
}
