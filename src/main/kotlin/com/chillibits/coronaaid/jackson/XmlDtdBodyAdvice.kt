package com.chillibits.coronaaid.jackson

import com.chillibits.coronaaid.shared.XmlDtdUrl
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import java.lang.reflect.Method
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class XmlDtdBodyAdvice: ResponseBodyAdvice<Any> {

    private val reflectionCache: MutableMap<Method, String?> = mutableMapOf()

    override fun supports(method: MethodParameter, converter: Class<out HttpMessageConverter<*>>) = method.method != null

    override fun beforeBodyWrite(
            obj: Any?,
            method: MethodParameter,
            mediaType: MediaType,
            converter: Class<out HttpMessageConverter<*>>,
            serverRequest: ServerHttpRequest, serverResponse: ServerHttpResponse): Any? {
        if(serverResponse is ServletServerHttpResponse && mediaType == MediaType.APPLICATION_XML) {
            val servletResponse = serverResponse.servletResponse

            if(servletResponse.status == 200 || servletResponse.status == 201) {
                handleMethod(serverResponse, method)
            }
        }

        return obj
    }

    private fun handleMethod(response: ServletServerHttpResponse, handler: MethodParameter): Int {
        if(reflectionCache.containsKey(handler.method)) {
            return writeToServlet(response.servletResponse, reflectionCache[handler.method])
        }

        val url = handler.getMethodAnnotation(XmlDtdUrl::class.java)?.url
        val rootElement = handler.getMethodAnnotation(XmlDtdUrl::class.java)?.rootElement.toString()
        var combined = XML_HEADER

        if(!url.isNullOrEmpty()) {
            combined += XML_DTD_TEMPLATE
                    .replace("%DTD_URL%", url)
                    .replace("%DTD_ROOT_ELEMENT%", rootElement)
        }

        reflectionCache[handler.method!!] = combined
        return writeToServlet(response.servletResponse, combined)
    }

    private fun writeToServlet(response : HttpServletResponse, data: String?): Int {
        return if(data != null) {
            val byteArray = data.toByteArray()
            response.outputStream.write(byteArray)
            byteArray.size
        } else 0
    }

    companion object {
        const val XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        const val XML_DTD_TEMPLATE = "<!DOCTYPE %DTD_ROOT_ELEMENT% SYSTEM \"%DTD_URL%\">"
    }
}