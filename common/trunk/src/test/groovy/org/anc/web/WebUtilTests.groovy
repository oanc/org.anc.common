package org.anc.web

import spock.lang.Specification

import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * @author Keith Suderman
 */
class WebUtilTest extends Specification {

    static String text = MediaType.TEXT_PLAIN
    static String json = MediaType.APPLICATION_JSON

    def "methods return a Response object"() {

        expect:
        object instanceof Response

        where:
        type     | object
        Response | WebUtil.ok()
        Response | WebUtil.ok('foo')
        Response | WebUtil.ok('foo', 'text/plain')
        Response | WebUtil.ok('foo', MediaType.TEXT_PLAIN)
        Response | WebUtil.status(200, "ok")
        Response | WebUtil.status(200, "ok", "text/plain")
        Response | WebUtil.status(200, "ok", MediaType.TEXT_PLAIN)
        Response | WebUtil.error("error")
    }

    def "check response codes"() {
        expect:
        expected == actual.status

        where:
        expected | actual
        200      | WebUtil.ok()
        200      | WebUtil.ok('foo')
        400      | WebUtil.status(400, "foo")
        404      | WebUtil.status(404, '{"name":"value"}', "application/json")
        500      | WebUtil.error("error")
    }

    def "check content types"() {

        expect:
        expected == response.metadata['Content-Type'][0].toString()

        where:
        expected | response
        text     | WebUtil.ok('foo', 'text/plain')
        text     | WebUtil.ok('foo', MediaType.TEXT_PLAIN)
        text     | WebUtil.status(420, 'Enhance your calm', 'text/plain')
        json     | WebUtil.status(200, "{}", 'application/json')
        json     | WebUtil.status(200, "{}", MediaType.APPLICATION_JSON)
    }


}
